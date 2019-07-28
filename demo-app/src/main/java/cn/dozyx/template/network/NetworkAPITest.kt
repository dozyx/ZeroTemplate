package cn.dozyx.template.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import cn.dozyx.core.utli.log.LogUtil
import cn.dozyx.core.utli.util.RxJavaUtil
import cn.dozyx.template.base.BaseShowResultActivity
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by zero on 2017/9/22.
 */

class NetworkAPITest : BaseShowResultActivity(), DownloadCallback {

    private var networkFragment: NetworkFragment? = null
    private var downloading = false

    internal var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkFragment = NetworkFragment.newInstance(supportFragmentManager, URL_STRING)
    }


    override fun getButtonText(): Array<String> {
        return arrayOf("HttpURLConnection", "Volley", "OkHttp", "Retrofit", "Rx+Retrofit")
    }

    override fun onButton1() {
        startDownload(NetworkFragment.DOWNLOAD_TYPE_HTTPS_URL_CONNECTION)
    }

    override fun onButton2() {
        val requests = Volley.newRequestQueue(this)
        requests.add(
                StringRequest(Request.Method.GET,
                        URL_STRING,
                        Response.Listener { s -> setText(s) },
                        Response.ErrorListener { volleyError -> setText(volleyError.message) })
        )

    }

    override fun onButton3() {
        startDownloadWithOkHttp3()
    }

    override fun onButton4() {
        startDownloadWithRetrofit()
    }

    override fun onButton5() {
        startDownloadWithRxRetrofit()
    }

    private fun startDownloadWithRxRetrofit() {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
        val retrofit = Retrofit.Builder().baseUrl(URL_STRING).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client).build()
        val iApi = retrofit.create(IApi::class.java)
        iApi.getCnBetaHome().repeat(2).compose(RxJavaUtil.applySchedulers2()).subscribe(Consumer {
            Timber.d("NetworkAPITest.startDownloadWithRxRetrofit")
        })
        Observable.interval(2,TimeUnit.SECONDS)
    }

    override fun updateFromDownload(result: String?) {
        if (result != null) {
            setText(result)
        }
    }

    override fun getActiveNetworkInfo(): NetworkInfo {
        val connectivityManager = getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        return connectivityManager.activeNetworkInfo
    }

    override fun onProgressUpdate(progressCode: Int, percentComplete: Int) {
        setText("code: $progressCode & percent: $percentComplete")
    }

    override fun finishDownloading() {
        downloading = false
        if (networkFragment != null) {
            networkFragment!!.cancelDownload()
        }
    }

    /**
     * 本来想要使用 type 来区分不同 API，不过因为开源库已实现异步，所以没用上，暂时保留
     */
    private fun startDownload(type: Int) {
        if (!downloading && networkFragment != null) {
            networkFragment!!.startDownload(type)
            downloading = true
        }
    }

    private fun startDownloadWithOkHttp3() {
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder().url(
                URL_STRING
        ).build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: okhttp3.Response) {
                val string = response.body()!!.string()
                // 该回调并不在 UI 线程
                runOnUiThread { setText(string) }
            }
        })
    }

    private fun startDownloadWithRetrofit() {
        val retrofit = Retrofit.Builder().baseUrl(URL_STRING).build()
        val service = retrofit.create(IBaiduHomePage::class.java)
        LogUtil.d("startDownloadWithRetrofit: " + service + " & " + retrofit.create(IBaiduHomePage::class.java))
        var call: retrofit2.Call<ResponseBody> = service.data
        for (i in 0..9) {
            if (call.isExecuted) {
                call = call.clone()
            }
            call.enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                        call: retrofit2.Call<ResponseBody>,
                        response: retrofit2.Response<ResponseBody>
                ) {
                    //                try {
                    //                    ToastX.showShort(NetworkAPITest.this,count +"");
                    //                    setText(response.body().string());
                    appendResult((++count).toString() + "")
                    //                } catch (IOException e) {
                    //                    e.printStackTrace();
                    //                }
                }

                override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                    appendResult("onFailure: " + t.message)
                }
            })
        }
    }

    private interface IBaiduHomePage {
        @get:GET("\\")
        val data: retrofit2.Call<ResponseBody>
    }

    companion object {
        private const val URL_STRING = "http://www.cnbeta.com/"
    }

}

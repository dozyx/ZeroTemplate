package cn.dozyx.template.network

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import cn.dozyx.core.rx.SchedulersTransformer
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import cn.dozyx.core.utli.util.RxJavaUtil
import cn.dozyx.template.base.Action
import cn.dozyx.template.base.BaseTestActivity
import com.blankj.utilcode.util.CloseUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.BufferedSink
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import timber.log.Timber
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by zero on 2017/9/22.
 */

class NetworkAPITest : BaseTestActivity(), DownloadCallback {
    override fun initActions() {

    }

    private var networkFragment: NetworkFragment? = null
    private var downloading = false

    internal var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkFragment = NetworkFragment.newInstance(supportFragmentManager, URL_STRING)
        addAction(object : Action("HttpURLConnection") {
            override fun run() {
                startDownload(NetworkFragment.DOWNLOAD_TYPE_HTTPS_URL_CONNECTION)
            }
        })
        addAction(object : Action("Volley") {
            override fun run() {
                val requests = Volley.newRequestQueue(this@NetworkAPITest)
                requests.add(
                        StringRequest(Request.Method.GET,
                                URL_STRING,
                                Response.Listener { s -> showResult(s) },
                                Response.ErrorListener { volleyError -> showResult(volleyError.message) })
                )
            }
        })
        addAction(object : Action("OkHttp") {
            override fun run() {
                startDownloadWithOkHttp3()
            }
        })
        addAction(object : Action("Retrofit") {
            override fun run() {
                startDownloadWithRetrofit()
            }
        })

        addAction(object : Action("404 error") {
            override fun run() {
                startErrorRequest()
            }
        })
        addAction(object : Action("Rx+Retrofit") {
            override fun run() {
                startDownloadWithRxRetrofit()
            }
        })

        addAction(object : Action("下载文件") {
            override fun run() {
                val uri = "https://d-02.winudf.com/b/apk/Y29tLmlmbHl0ZWsuaW5wdXRtZXRob2QuZ29vZ2xlXzk0MzFfOGJiY2UyNzQ?_fn=6K6v6aOe6L6T5YWl5rOVX3Y5LjEuOTQzMV9hcGtwdXJlLmNvbS5hcGs&_p=Y29tLmlmbHl0ZWsuaW5wdXRtZXRob2QuZ29vZ2xl&k=271708fb5ec7937fd520b92001cab4b35da03780&uu=http%3A%2F%2F172.16.64.1%2Fb%2Fapk%2FY29tLmlmbHl0ZWsuaW5wdXRtZXRob2QuZ29vZ2xlXzk0MzFfOGJiY2UyNzQ%3Fk%3D81b7d0ac0dceb1a44cb15d459fbbc56b5da03780"
                startDownloadFile(uri)
            }
        })
        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe()
        val intentFilter = IntentFilter()
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED)
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.apply {
                    appendResult(action)
                }
            }
        }, intentFilter)
    }

    private fun startErrorRequest() {
        val service = createService()
        Timber.d("NetworkAPITest.startDownloadWithRetrofit")
        var call: retrofit2.Call<ResponseBody> = service.doErrorRequest()
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                    call: retrofit2.Call<ResponseBody>,
                    response: retrofit2.Response<ResponseBody>
            ) {
                appendResult((++count).toString() + " " + response.code() + " " + response.body())
            }

            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                appendResult("onFailure: " + t.message)
            }
        })
    }

    private fun startDownloadFile(uri: String) {
//        downloadWithTask(uri)
//        downloadWithManager(uri)
        downloadWithRetrofit(uri)
    }

    private fun downloadWithRetrofit(uri: String) {
        createService().downApk(uri).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                appendResult("onFailure ${t.message}")
            }

            override fun onResponse(call: retrofit2.Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                appendResult("onResponse " + Thread.currentThread())
                Thread(Runnable {
                    response.body()?.apply {
                        val inputStream = byteStream()
                        val fileSize = contentLength()
                        val outputStream = FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + "test3.apk")
                        try {
                            val datas = ByteArray(4096)
                            var total = 0L
                            var count = inputStream.read(datas)
                            var oldProgress = 0
                            while (count != -1) {
                                outputStream.write(datas, 0, count)
                                total += count
                                val progress = (total * 100 / fileSize).toInt()
                                if (progress != oldProgress) {
                                    appendResult("$progress %")
                                    oldProgress = progress
                                }
                                count = inputStream.read(datas)
                            }
                        } catch (e: Exception) {
                            appendResult(e.toString())
                        } finally {
                            CloseUtils.closeIO(inputStream, outputStream)
                        }
                    }

                }).start()
            }
        })
    }

    private fun downloadWithManager(uri: String) {
        val request = DownloadManager.Request(Uri.parse(uri))
        request.setDescription("下载文件描述")
        request.setTitle("下载标题")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "test1.apk")
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

    private fun downloadWithTask(uri: String) {
        DownloadTask(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + "test.apk").execute(uri)
    }

    inner class DownloadTask(private val filePath: String) : AsyncTask<String, Int, String>() {
        override fun doInBackground(vararg params: String?): String {
            var input: InputStream? = null
            var output: OutputStream? = null
            var connection: HttpURLConnection? = null
            val url = URL(params[0])
            var oldProgress = 0
            try {
                connection = url.openConnection() as HttpURLConnection
                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    return "下载地址错误：${connection.responseCode} ${connection.responseMessage}"
                }
                val fileLength = connection.contentLength

                input = connection.inputStream
                output = FileOutputStream(filePath)
                val datas = ByteArray(4096)
                var total = 0
                var count = input.read(datas)
                while (count != -1) {
                    if (isCancelled) {
                        input.close()
                        return ""
                    }
                    total += count
                    if (fileLength > 0) {
                        val newProgress = total * 100 / fileLength
                        if (newProgress != oldProgress) {
                            appendResult("$newProgress%")
                            oldProgress = newProgress
                        }
                    }
                    output.write(datas, 0, count)
                    count = input.read(datas)
                }
            } catch (e: Exception) {
                appendResult(e.toString())
                return e.toString()
            } finally {
                CloseUtils.closeIO(output, input)
                connection?.apply {
                    disconnect()
                }
            }
            return ""
        }
    }

    private fun startDownloadWithRxRetrofit() {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
        val retrofit = Retrofit.Builder().baseUrl(URL_STRING).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(client).build()
        val iApi = retrofit.create(IApi::class.java)
        iApi.getCategories().compose(SchedulersTransformer.get()).subscribe(object : Observer<String> {
            override fun onComplete() {
                Timber.d("NetworkAPITest.onComplete")
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: String) {
                Timber.d("NetworkAPITest.onNext: $t")
            }

            override fun onError(e: Throwable) {
                Timber.d("NetworkAPITest.onError")
            }
        })
    }

    override fun updateFromDownload(result: String?) {
        if (result != null) {
            showResult(result)
        }
    }

    override fun getActiveNetworkInfo(): NetworkInfo {
        val connectivityManager = getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        return connectivityManager.activeNetworkInfo
    }

    override fun onProgressUpdate(progressCode: Int, percentComplete: Int) {
        showResult("code: $progressCode & percent: $percentComplete")
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
                runOnUiThread { showResult(string) }
            }
        })
    }

    private fun createService(): IService {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        val client = OkHttpClient.Builder().addInterceptor() { chain ->
            Timber.d("NetworkAPITest addInterceptor")
            var response = chain.proceed(chain.request())
            if (response.code() != 404) {
                return@addInterceptor response
            }
            response.newBuilder().code(200).build()
        }.addInterceptor(httpLoggingInterceptor).build()
        val retrofit = Retrofit.Builder().baseUrl(URL_STRING).client(client).build()
        return retrofit.create(IService::class.java)
    }

    private fun startDownloadWithRetrofit() {
        val service = createService()
        Timber.d("NetworkAPITest.startDownloadWithRetrofit")
        var call: retrofit2.Call<ResponseBody> = service.data
//        for (i in 0..9) {
        if (call.isExecuted) {
            call = call.clone()
        }
        /*call.enqueue(object : retrofit2.Callback<ResponseBody> {
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
        })*/
        service.foo.execute()
//        }
    }

    private interface IService {
        @get:GET("index")
        val data: retrofit2.Call<ResponseBody>

        @get:GET("index")
        val foo: retrofit2.Call<retrofit2.Response<String>>

        @Streaming
        @GET
        fun downApk(@Url uri: String): retrofit2.Call<ResponseBody>

        @GET("/error")
        fun doErrorRequest(): retrofit2.Call<ResponseBody>
    }

    private class Body : RequestBody() {
        override fun contentType(): MediaType? {
            return null
        }

        override fun writeTo(sink: BufferedSink) {
        }
    }

    companion object {
        private const val URL_STRING = "http://www.cnbeta.com/"
    }

}

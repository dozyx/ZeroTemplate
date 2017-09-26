package com.zerofate.template.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.zerofate.template.base.BaseShowResultActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by zero on 2017/9/22.
 */

public class NetworkAPITest extends BaseShowResultActivity implements DownloadCallback {
    private static final String TAG = "NetworkAPITest";
    private static final String URL_STRING = "https://www.baidu.com";

    private NetworkFragment networkFragment;
    private boolean downloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkFragment = NetworkFragment.newInstance(getSupportFragmentManager(), URL_STRING);
    }


    @Override
    protected String[] getButtonText() {
        return new String[]{"HttpURLConnection", "Volley", "OkHttp", "Retrofit"};
    }

    @Override
    public void onButton1() {
        startDownload(NetworkFragment.DOWNLOAD_TYPE_HTTPS_URL_CONNECTION);
    }

    @Override
    public void onButton2() {
        RequestQueue requests = Volley.newRequestQueue(this);
        requests.add(new StringRequest(Request.Method.GET, URL_STRING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        setText(s);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                setText(volleyError.getMessage());
            }
        }));

    }

    @Override
    public void onButton3() {
        startDownloadWithOkHttp3();
    }

    @Override
    public void onButton4() {
        startDownloadWithRetrofit();
    }

    @Override
    public void updateFromDownload(String result) {
        if (result != null) {
            setText(result);
        }
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
                CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        setText("code: " + progressCode + " & percent: " + percentComplete);
    }

    @Override
    public void finishDownloading() {
        downloading = false;
        if (networkFragment != null) {
            networkFragment.cancelDownload();
        }
    }

    /**
     * 本来想要使用 type 来区分不同 API，不过因为开源库已实现异步，所以没用上，暂时保留
     */
    private void startDownload(int type) {
        if (!downloading && networkFragment != null) {
            networkFragment.startDownload(type);
            downloading = true;
        }
    }

    private void startDownloadWithOkHttp3() {
        OkHttpClient client = new OkHttpClient();
        final okhttp3.Request request = new okhttp3.Request.Builder().url(
                URL_STRING).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                final String string = response.body().string();
                // 该回调并不在 UI 线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setText(string);
                    }
                });
            }
        });
    }

    private void startDownloadWithRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_STRING).build();
        IBaiduHomePage service = retrofit.create(IBaiduHomePage.class);
        retrofit2.Call<ResponseBody> call = service.getData();
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call,
                    retrofit2.Response<ResponseBody> response) {
                try {
                    setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private interface IBaiduHomePage {
        @GET
        retrofit2.Call<ResponseBody> getData();
    }

}

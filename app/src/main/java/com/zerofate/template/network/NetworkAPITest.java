package com.zerofate.template.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zerofate.template.base.BaseShowResultActivity;

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
    public void onButton4() {
    }

    @Override
    public void updateFromDownload(String result) {
        if (result != null) {
            setResult(result);
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
        setResult("code: " + progressCode + " & percent: " + percentComplete);
    }

    @Override
    public void finishDownloading() {
        downloading = false;
        if (networkFragment != null) {
            networkFragment.cancelDownload();
        }
    }

    private void startDownload(int type) {
        if (!downloading && networkFragment != null) {
            networkFragment.startDownload(type);
            downloading = true;
        }
    }
}

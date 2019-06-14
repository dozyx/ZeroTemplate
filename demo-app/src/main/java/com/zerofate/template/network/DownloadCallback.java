package com.zerofate.template.network;

import android.net.NetworkInfo;

/**
 * Created by zero on 2017/9/22.
 */

public interface DownloadCallback {
    interface Progress {
        int ERROR = -1;
        int CONNECT_SUCCESS = 0;
        int GET_INPUT_STREAM_SUCCESS = 1;
        int PROCESS_INPUT_STREAM_IN_PROGRESS = 2;// 正在处理输入流
        int PROCESS_INPUT_STREAM_SUCCESS = 3;
    }

    void updateFromDownload(String result);

    NetworkInfo getActiveNetworkInfo();

    void onProgressUpdate(int progressCode, int percentComplete);

    void finishDownloading();
}

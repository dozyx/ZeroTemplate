package com.zerofate.template.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * 参考：
 * https://github.com/googlesamples/android-NetworkConnect/blob/master/Application/src/main/java
 * /com/example/android/networkconnect/NetworkFragment.java
 */

public class NetworkFragment extends Fragment {
    public static final String TAG = "NetworkFragment";
    public static final int DOWNLOAD_TYPE_HTTPS_URL_CONNECTION = 0;
    public static final int DOWNLOAD_TYPE_VOLLEY = 1;
    public static final int DOWNLOAD_TYPE_OK_HTTP = 2;
    public static final int DOWNLOAD_TYPE_RETROFIT = 3;

    private static final String URL_KEY = "UrlKey";

    private DownloadCallback callback;
    private DownloadTask downloadTask;
    private String urlString;

    public static NetworkFragment newInstance(FragmentManager fragmentManager, String url) {
        // 先进行恢复（Activity 因配置变化导致重建的情况）
        NetworkFragment networkFragment = (NetworkFragment) fragmentManager.findFragmentByTag(TAG);
        if (networkFragment == null) {
            networkFragment = new NetworkFragment();
            Bundle args = new Bundle();
            args.putString(URL_KEY, url);
            networkFragment.setArguments(args);
            fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
        }
        return networkFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        urlString = getArguments().getString(URL_KEY);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (DownloadCallback) context;
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        cancelDownload();
        super.onDestroy();
    }

    public void startDownload(int type) {
        cancelDownload();
        switch (type) {
            case DOWNLOAD_TYPE_VOLLEY:
                break;
            case DOWNLOAD_TYPE_OK_HTTP:
                break;
            case DOWNLOAD_TYPE_RETROFIT:
                break;
            default:
                downloadTask = getTaskByHttpURLConnection();
        }
        downloadTask.execute(urlString);
    }

    public void cancelDownload() {
        if (downloadTask != null) {
            downloadTask.cancel(true);
            downloadTask = null;
        }
    }

    private DownloadTask getTaskByHttpURLConnection() {
        return new DownloadTask() {
            @Override
            public String downloadUrl(String urlString) throws IOException {
                URL url = null;
                try {
                    url = new URL(urlString);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                InputStream stream = null;
                HttpsURLConnection connection = null;
                String result = null;
                try {
                    connection = (HttpsURLConnection) url.openConnection();
                    connection.setReadTimeout(3000);
                    connection.setConnectTimeout(3000);
                    connection.setRequestMethod("GET");
                    // google官方源码解释
                    // Already true by default but setting just in case; needs to be true since this
                    // request
                    // is carrying an input (response) body.
                    connection.setDoInput(true);
                    connection.connect();
                    publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS);
                    int responseCode = connection.getResponseCode();
                    if (responseCode != HttpsURLConnection.HTTP_OK) {
                        throw new IOException("Http error code:" + responseCode);
                    }
                    stream = connection.getInputStream();
                    publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0);
                    if (stream != null) {
                        result = readStream(stream);
                        publishProgress(DownloadCallback.Progress.PROCESS_INPUT_STREAM_IN_PROGRESS,
                                0);
                    }
                } finally {
                    if (stream != null) {
                        stream.close();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return result;
            }
        };
    }

    /**
     * 其实可以将下载理解为获取输入流的过程
     */
    private abstract class DownloadTask extends AsyncTask<String, Integer, DownloadTask.Result> {
        class Result {
            public String resultValue;
            public Exception exception;

            public Result(String resultValue) {
                this.resultValue = resultValue;
            }

            public Result(Exception exception) {
                this.exception = exception;
            }
        }


        /**
         * 无网络情况下取消操作
         */
        @Override
        protected void onPreExecute() {
            if (callback == null) {
                return;
            }
            NetworkInfo networkInfo = callback.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected() || (networkInfo.getType() !=
                    ConnectivityManager.TYPE_MOBILE
                    && networkInfo.getType() != ConnectivityManager.TYPE_WIFI)) {
                callback.updateFromDownload(null);
                cancel(true);
            }
        }

        @Override
        protected Result doInBackground(String... urls) {
            Result result = null;
            if (isCancelled() || urls == null || urls.length == 0) {
                return null;
            }
            String urlString = urls[0];
            try {
                String resultString = downloadUrl(urlString);
                if (resultString != null) {
                    result = new Result(resultString);
                } else {
                    throw new IOException("未接到任何响应!");
                }
            } catch (Exception e) {
                result = new Result(e);
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values.length >= 2) {
                callback.onProgressUpdate(values[0], values[1]);
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            if (result == null || callback == null) {
                return;
            }
            if (result.exception != null) {
                callback.updateFromDownload(result.exception.getMessage());
            } else if (result.resultValue != null) {
                callback.updateFromDownload(result.resultValue);
            }
            callback.finishDownloading();
        }


        protected String readStream(InputStream stream) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }

        protected String readStream(InputStream stream, int maxLength) throws IOException {
            String result = null;
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[maxLength];
            int numChars = 0;// 全部读取的字符数
            int readSize = 0;// 该次实际读取字符数
            while (numChars < maxLength && readSize != -1) {
                // 长度未满足要求且未读取到最后字符
                numChars += readSize;
                int pct = (100 * numChars) / maxLength;
                publishProgress(DownloadCallback.Progress.PROCESS_INPUT_STREAM_IN_PROGRESS, pct);
                readSize = reader.read(buffer, numChars, buffer.length - numChars);
            }
            if (numChars != -1) {
                numChars = Math.min(numChars, maxLength);
                result = new String(buffer, 0, numChars);
            }
            return result;
        }

        public abstract String downloadUrl(String urlString) throws IOException;

    }

}

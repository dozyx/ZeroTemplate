package com.zerofate.android.zerotemplate.socket;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zerofate.android.zerotemplate.R;
import com.zerofate.android.zerotemplate.util.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 《Android开发艺术探索》
 * 使用TCP进行socket连接
 */
public class SocketActivity extends AppCompatActivity {

    private static final int MSG_RECEIVE_NEW_MSG = 0;
    private static final int MSG_SOCKET_CONNECTED = 1;

    @BindView(R.id.message_edit)
    EditText mSendEdit;
    @BindView(R.id.send)
    Button mSendBtn;
    @BindView(R.id.shown_message)
    TextView mShownText;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RECEIVE_NEW_MSG:
                    mShownText.setText(mShownText.getText() + (String) msg.obj);
                    break;
                case MSG_SOCKET_CONNECTED:
                    mSendBtn.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);

        startService(new Intent(this, SocketService.class));// 启动Socket服务端
        new Thread() {
            @Override
            public void run() {
                connectSocketServer();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onDestroy();
    }

    @OnClick(R.id.send)
    public void onClick() {
        final String msg = mSendEdit.getText().toString();
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
            new Thread(){
                @Override
                public void run() {
                    mPrintWriter.println(msg);
                    // 书中没有使用子线程，但我实际使用中没有子线程会抛出NetworkOnMainThreadException
                    // 异常设备小米5，7.0系统，使用虚拟设备Android 4.4没问题，所以可能是Google后来策略做了修改
                }
            }.start();

            mSendEdit.setText("");
            String time = Utils.formatDateTime(System.currentTimeMillis());
            final String shownMsg = "client:" + time + ":" + msg + "\n";
            mShownText.setText(mShownText.getText() + shownMsg);
        }
    }


    private void connectSocketServer() {
        Socket socket = null;
        while (socket == null) {
            // 连接服务端，失败将重连
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MSG_SOCKET_CONNECTED);
            } catch (IOException e) {
                SystemClock.sleep(1000);
            }
        }
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            while (!SocketActivity.this.isFinishing()) {
                String msg = reader.readLine();
                if (msg != null) {
                    final String shownMsg = "server " + Utils.formatDateTime(
                            System.currentTimeMillis()) + ":" + msg + "\n";
                    mHandler.obtainMessage(MSG_RECEIVE_NEW_MSG, shownMsg).sendToTarget();
                }
            }
            mPrintWriter.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

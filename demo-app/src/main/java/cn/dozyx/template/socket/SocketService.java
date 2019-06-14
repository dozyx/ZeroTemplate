package cn.dozyx.template.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import cn.dozyx.template.util.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SocketService extends Service {
    private boolean mIsServiceDestoryed = false;

    public SocketService() {
    }

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {
        @Override
        public void run() {
            // server socket 等待来着网络的请求，并基于请求执行操作，它还可以为请求者返回一个结果
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!mIsServiceDestoryed) {
                // 接收客户端请求
                try {
                    final Socket client = serverSocket.accept();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 响应客户端请求
     */
    private void responseClient(Socket client) throws IOException {
        //接收客户端信息
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //向客户端发送信息
        PrintWriter writer = new PrintWriter(
                new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        writer.println("欢迎来到聊天室！");
        while (!mIsServiceDestoryed) {
            // 不断地响应该 client 的请求
            String str = reader.readLine();
            if (str == null) {
                break;//断开
            }
            int i = new Random().nextInt(Constants.sMessages.length);
            writer.println(str + ": " + Constants.sMessages[i]);
        }
        writer.close();
        reader.close();
        client.close();
    }
}

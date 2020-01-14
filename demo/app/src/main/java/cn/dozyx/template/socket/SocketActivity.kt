package cn.dozyx.template.socket

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import cn.dozyx.template.R
import cn.dozyx.template.util.Utils
import kotlinx.android.synthetic.main.activity_socket.*
import java.io.*
import java.lang.StringBuilder
import java.net.Socket

/**
 * 《Android开发艺术探索》
 * 使用TCP进行socket连接
 *
 * https://www.jianshu.com/p/089fb79e308b
 * Socket 连接过程：
 * 客户端：
 *      1. 创建 Socket 实例
 *      2. 系统分配本地端口号
 *      3. 系统创建一个含本地、远程地址、端口号的套接字数据结构
 *      4. 在创建 Socket 实例的构造函数正确返回前，进行 TCP 的三次握手协议
 *      5. TCP 握手协议完成后，Socket 实例对象将创建完成
 * 服务端：
 *      1. 创建 SocketServer 实例
 *      2. 系统为 SocketServer 实例创建一个底层数据结构
 *      3. 调用 accept() 方法时，将进入阻塞状态，等待客户端的请求
 *      4. 当一个新的请求到来时，将为该连接创建一个新的套接字数据结构
 *      5. 等三次握手完成后，该服务端的 Socket 实例才会返回，并将该 Socket 实例对应的数据结构从未完成列表中移到已完成列表中
 *
 * 类型：1. 流套接字 streamsocket，基于 TCP；2. 数据报套接字 datagramsocket，基于 UDP
 *
 */
class SocketActivity : AppCompatActivity() {

    private var mPrintWriter: PrintWriter? = null
    private var mClientSocket: Socket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket)
        send.setOnClickListener { sendMessage() }

        startService(Intent(this, SocketService::class.java))// 启动Socket服务端
        object : Thread() {
            override fun run() {
                connectSocketServer()
            }
        }.start()
    }

    override fun onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket!!.shutdownInput()
                mClientSocket!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        super.onDestroy()
    }

    private fun sendMessage() {
        val msg = message_edit.text.toString()
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
            object : Thread() {
                override fun run() {
                    mPrintWriter!!.println(msg)
                    // 书中没有使用子线程，但我实际使用中没有子线程会抛出NetworkOnMainThreadException
                    // 异常设备小米5，7.0系统，使用虚拟设备Android 4.4没问题，所以可能是Google后来策略做了修改
                }
            }.start()

            message_edit.setText("")
            val time = Utils.formatDateTime(System.currentTimeMillis())
            val newMsg = "client:$time:$msg\n"
            shown_message.text = StringBuilder().append(newMsg).append("\n").append(shown_message.text.toString()).toString()
        }
    }


    private fun connectSocketServer() {
        var socket: Socket? = null
        while (socket == null) {
            // 连接服务端，失败将重连
            try {
                // 创建客户端
                socket = Socket("localhost", 8688)
                mClientSocket = socket
                // 通过 outputStream 向服务端发送数据
                mPrintWriter = PrintWriter(
                        BufferedWriter(OutputStreamWriter(socket.getOutputStream())), true)
                runOnUiThread {
                    send.isEnabled = true
                }
            } catch (e: IOException) {
                SystemClock.sleep(1000)
            }

        }
        try {
            // 通过 InputStream 获取服务端返回的数据
            val reader = BufferedReader(
                    InputStreamReader(socket.getInputStream()))
            while (!this@SocketActivity.isFinishing) {
                val msg = reader.readLine()
                if (msg != null) {
                    val shownMsg = "server " + Utils.formatDateTime(
                            System.currentTimeMillis()) + ":" + msg + "\n"
                    runOnUiThread {
                        showReceiveMessage(shownMsg)
                    }
                }
            }
            mPrintWriter!!.close()
            reader.close()
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun showReceiveMessage(msg: String) {
        val original = shown_message.text
        shown_message.text = StringBuilder().append(msg).append("\n").append(original).toString()
    }

}

package cn.dozyx.template.socket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import cn.dozyx.template.R
import cn.dozyx.template.util.Utils
import kotlinx.android.synthetic.main.activity_socket.*
import java.io.*
import java.net.Socket

/**
 * 《Android开发艺术探索》
 * 使用TCP进行socket连接
 */
class SocketActivity : AppCompatActivity() {

    private var mPrintWriter: PrintWriter? = null
    private var mClientSocket: Socket? = null

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_RECEIVE_NEW_MSG -> shown_message!!.text = shown_message!!.text.toString() + msg.obj as String
                MSG_SOCKET_CONNECTED -> send!!.isEnabled = true
                else -> {
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socket)
        ButterKnife.bind(this)

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

    @OnClick(R.id.send)
    fun onClick() {
        val msg = message_edit!!.text.toString()
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
            object : Thread() {
                override fun run() {
                    mPrintWriter!!.println(msg)
                    // 书中没有使用子线程，但我实际使用中没有子线程会抛出NetworkOnMainThreadException
                    // 异常设备小米5，7.0系统，使用虚拟设备Android 4.4没问题，所以可能是Google后来策略做了修改
                }
            }.start()

            message_edit!!.setText("")
            val time = Utils.formatDateTime(System.currentTimeMillis())
            val shownMsg = "client:$time:$msg\n"
            shown_message!!.text = shown_message!!.text.toString() + shownMsg
        }
    }


    private fun connectSocketServer() {
        var socket: Socket? = null
        while (socket == null) {
            // 连接服务端，失败将重连
            try {
                socket = Socket("localhost", 8688)
                mClientSocket = socket
                mPrintWriter = PrintWriter(
                        BufferedWriter(OutputStreamWriter(socket.getOutputStream())), true)
                mHandler.sendEmptyMessage(MSG_SOCKET_CONNECTED)
            } catch (e: IOException) {
                SystemClock.sleep(1000)
            }

        }
        try {
            val reader = BufferedReader(
                    InputStreamReader(socket.getInputStream()))
            while (!this@SocketActivity.isFinishing) {
                val msg = reader.readLine()
                if (msg != null) {
                    val shownMsg = "server " + Utils.formatDateTime(
                            System.currentTimeMillis()) + ":" + msg + "\n"
                    mHandler.obtainMessage(MSG_RECEIVE_NEW_MSG, shownMsg).sendToTarget()
                }
            }
            mPrintWriter!!.close()
            reader.close()
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    companion object {

        private val MSG_RECEIVE_NEW_MSG = 0
        private val MSG_SOCKET_CONNECTED = 1
    }


}

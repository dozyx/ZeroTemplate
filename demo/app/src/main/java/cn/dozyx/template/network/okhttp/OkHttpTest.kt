package cn.dozyx.template.network.okhttp

import cn.dozyx.template.base.BaseTestActivity
import cn.dozyx.template.imageloader.DiskLruCache
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.Call
import okhttp3.EventListener
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class OkHttpTest : BaseTestActivity() {
    private val okHttpClient: OkHttpClient by lazy {
        val cache = Cache(
            File(cacheDir, "test_okhttp"),
            20 shl 20
        )
        OkHttpClient.Builder()
//            .cache(cache)
            .eventListener(LogEventListener())
            .build()
    }

    override fun initActions() {
        addAction("test") {
            GlobalScope.launch {
                val start = System.currentTimeMillis()
                val builder = Request.Builder()
                    .url(TEST_URL)
                    .addHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Linux; Android 13; M2102K1AC Build/TKQ1.220829.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/116.0.0.0 Mobile Safari/537.36"
                    )
                    .addHeader("x-requested-with-version", "7.07.1.7070001.7070001")
                val call = okHttpClient.newCall(builder.build())
                Timber.d("http execute1: ${System.currentTimeMillis() - start}")
                val response = call.execute()
                val code = response.code
                val string = response.body?.string()
                Timber.d("http execute2: ${System.currentTimeMillis() - start}")
                response.close()
            }
        }

        addAction("system") {
            GlobalScope.launch {
                val start = System.currentTimeMillis()
                val uri = URL(TEST_URL)
                val connection = uri.openConnection() as HttpURLConnection
                connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Linux; Android 13; M2102K1AC Build/TKQ1.220829.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/116.0.0.0 Mobile Safari/537.36"
                )
                connection.setRequestProperty("x-requested-with-version", "7.07.1.7070001.7070001")
                connection.doInput = true
                Timber.d("http execute1: ${System.currentTimeMillis() - start}")
                connection.connect()
                Timber.d("http execute2: ${System.currentTimeMillis() - start}")
                connection.responseCode
                // https://stackoverflow.com/questions/9943351/httpsurlconnection-and-keep-alive
                // 只有 input stream 比读取，重复请求才不会触发 DNS 和 Connect
                val string = inputStreamToString(connection.inputStream)
                Timber.d("http execute3: ${System.currentTimeMillis() - start}")
            }
        }

        addAction("head") {
            GlobalScope.launch {
                val builder = Request.Builder()
                    .url(TEST_HEAD_URL)
                    .head()
                val call = okHttpClient.newCall(builder.build())
                call.execute()
            }
        }
    }

    @Throws(IOException::class)
    private fun inputStreamToString(`in`: InputStream): String? {
        return DiskLruCache.readFully(InputStreamReader(`in`, Charset.forName("UTF-8")))
    }

    @Throws(IOException::class)
    fun readFully(reader: Reader): String? {
        return try {
            val writer = StringWriter()
            val buffer = CharArray(1024)
            var count: Int
            while (reader.read(buffer).also { count = it } != -1) {
                writer.write(buffer, 0, count)
            }
            writer.toString()
        } finally {
            reader.close()
        }
    }

    companion object {
        private const val TEST_URL = ""
        private const val TEST_HEAD_URL = ""
    }
}

private class LogEventListener : EventListener() {
    private var startTime = -1L
    override fun callEnd(call: Call) {
        Timber.d("http execute callEnd: ${System.currentTimeMillis() - startTime}")
    }

    override fun callStart(call: Call) {
        startTime = System.currentTimeMillis()
    }
}
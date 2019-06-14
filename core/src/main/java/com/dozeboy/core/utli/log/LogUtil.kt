package com.dozeboy.core.utli.log

import android.content.Context
import android.text.TextUtils
import com.dozeboy.core.ex.debuggable
import org.jetbrains.annotations.NonNls
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.StringReader
import java.io.StringWriter
import java.util.*
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * 参照 Logger 增加了 json、xml、数组对象格式化支持
 * @author dozeboy
 * @date 2018/7/26
 */
class LogUtil private constructor() {
    companion object : ILog {
        const val TAG = "LogUtil"
        var isDebug = false
        private const val JSON_INDENT = 2

        fun init(context: Context, debugTree: Timber.Tree, releaseTree: Timber.Tree) {
            if (context.debuggable()) {
                Timber.plant(debugTree)
            } else {
                Timber.plant(releaseTree)
            }
        }

        override fun v(@NonNls message: String, vararg args: Any) {
            Timber.v(message, *args)
        }

        override fun v(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.v(t, message, *args)
        }

        override fun v(t: Throwable) {
            Timber.v(t)
        }

        override fun d(@NonNls message: String, vararg args: Any) {
            Timber.d(message, *args)
        }

        override fun d(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.d(t, message, *args)
        }

        override fun d(t: Throwable) {
            Timber.d(t)
        }

        override fun d(any: Any?) {
            Timber.d(asString(any))
        }

        override fun i(@NonNls message: String, vararg args: Any) {
            Timber.i(message, *args)
        }

        override fun i(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.i(t, message, *args)
        }

        override fun i(t: Throwable) {
            Timber.i(t)
        }

        override fun w(@NonNls message: String, vararg args: Any) {
            Timber.w(message, *args)
        }

        override fun w(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.w(t, message, *args)
        }

        override fun w(t: Throwable) {
            Timber.w(t)
        }

        override fun e(@NonNls message: String, vararg args: Any) {
            Timber.e(message, *args)
        }

        override fun e(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.e(t, message, *args)
        }

        override fun e(t: Throwable) {
            Timber.e(t)
        }

        override fun wtf(@NonNls message: String, vararg args: Any) {
            Timber.wtf(message, *args)
        }

        override fun wtf(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.wtf(t, message, *args)
        }

        override fun wtf(t: Throwable) {
            Timber.wtf(t)
        }

        override fun json(json: String) {
            jsonInternal(json)
        }

        override fun xml(xml: String) {
            xmlInternal(xml)
        }

        /**
         * 该方法是为了增加一层调用，这样 Logger 在打印时才能使用与其他打印一致的方法偏移数来定位打印位置
         */
        private fun jsonInternal(json: String) {
            if (TextUtils.isEmpty(json)) {
                Timber.asTree().d("Empty/Null json content")
                return
            }
            try {
                var mutableJson = json.trim { it <= ' ' }
                if (mutableJson.startsWith("{")) {
                    val jsonObject = JSONObject(mutableJson)
                    val message = jsonObject.toString(JSON_INDENT)
                    Timber.asTree().d(message)
                    return
                }
                if (mutableJson.startsWith("[")) {
                    val jsonArray = JSONArray(mutableJson)
                    val message = jsonArray.toString(JSON_INDENT)
                    Timber.asTree().d(message)
                    return
                }
                Timber.asTree().e("Invalid Json")
            } catch (e: JSONException) {
                Timber.asTree().e("Invalid Json")
            }
        }

        private fun xmlInternal(xml: String) {
            if (TextUtils.isEmpty(xml)) {
                Timber.asTree().d("Empty/Null xml content")
                return
            }
            try {
                val xmlInput = StreamSource(StringReader(xml))
                val xmlOutput = StreamResult(StringWriter())
                val transformer = TransformerFactory.newInstance().newTransformer()
                transformer.setOutputProperty(OutputKeys.INDENT, "yes")
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
                transformer.transform(xmlInput, xmlOutput)
                Timber.asTree().d(xmlOutput.writer.toString().replaceFirst(">".toRegex(), ">\n"))
            } catch (e: TransformerException) {
                Timber.asTree().e("Invalid xml")
            }
        }

        private fun asString(any: Any?): String {
            if (any == null) {
                return "null"
            }
            if (!any.javaClass.isArray) {
                return any.toString()
            }
            if (any is BooleanArray) {
                return Arrays.toString(any as BooleanArray?)
            }
            if (any is ByteArray) {
                return Arrays.toString(any as ByteArray?)
            }
            if (any is CharArray) {
                return Arrays.toString(any as CharArray?)
            }
            if (any is ShortArray) {
                return Arrays.toString(any as ShortArray?)
            }
            if (any is IntArray) {
                return Arrays.toString(any as IntArray?)
            }
            if (any is LongArray) {
                return Arrays.toString(any as LongArray?)
            }
            if (any is FloatArray) {
                return Arrays.toString(any as FloatArray?)
            }
            if (any is DoubleArray) {
                return Arrays.toString(any as DoubleArray?)
            }
            return if (any is Array<*>) {
                Arrays.deepToString(any as Array<*>?)
            } else "Couldn't find a correct type for the object"
        }
    }


}
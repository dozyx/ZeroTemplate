package com.dozeboy.android.core.utli.log

import android.text.TextUtils
import com.zerofate.android.core.BuildConfig
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
class ZLog private constructor() {

    companion object {
        const val TAG = "ZLog"
        private const val JSON_INDENT = 2

        init {
            Timber.plant(if (BuildConfig.DEBUG) LoggerLogcatTree() else RemoteLogTree())
        }

        fun v(@NonNls message: String, vararg args: Any) {
            Timber.v(message, *args)
        }

        fun v(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.v(t, message, *args)
        }

        fun v(t: Throwable) {
            Timber.v(t)
        }

        fun d(@NonNls message: String, vararg args: Any) {
            Timber.d(message, *args)
        }

        fun d(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.d(t, message, *args)
        }

        fun d(t: Throwable) {
            Timber.d(t)
        }

        fun d(any: Any?) {
            Timber.d(asString(any))
        }

        fun i(@NonNls message: String, vararg args: Any) {
            Timber.i(message, *args)
        }

        fun i(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.i(t, message, *args)
        }

        fun i(t: Throwable) {
            Timber.i(t)
        }

        fun w(@NonNls message: String, vararg args: Any) {
            Timber.w(message, *args)
        }

        fun w(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.w(t, message, *args)
        }

        fun w(t: Throwable) {
            Timber.w(t)
        }

        fun e(@NonNls message: String, vararg args: Any) {
            Timber.e(message, *args)
        }

        fun e(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.e(t, message, *args)
        }

        fun e(t: Throwable) {
            Timber.e(t)
        }

        fun wtf(@NonNls message: String, vararg args: Any) {
            Timber.wtf(message, *args)
        }

        fun wtf(t: Throwable, @NonNls message: String, vararg args: Any) {
            Timber.wtf(t, message, *args)
        }

        fun wtf(t: Throwable) {
            Timber.wtf(t)
        }

        fun json(json: String) {
            jsonInternal(json)
        }

        fun xml(xml: String) {
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
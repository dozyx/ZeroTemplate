package cn.dozyx.core.http

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author dozyx
 * @date 2019-10-13
 */
object HttpManager {
    var isDebug: Boolean = false
    private val serviceCache: Map<ServiceWrapper, Class<*>> = HashMap()
    var commonInterceptor: Interceptor? = null

    fun <T> create(serviceClz: Class<T>): T? {
        return null
    }


    private fun getBaseRetrofitBuilder(): Retrofit.Builder {
        val builder = Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create())
        return builder
    }

    private fun getBaseOkHttpBuilder(): OkHttpClient.Builder {

        val builder = OkHttpClient.Builder()
        // okhttp 默认超时为 10s，没有规定的话，暂时保留默认
        if (isDebug) {
            var loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        if (commonInterceptor != null) {
//            builder.addInterceptor(commonInterceptor)
        }
        return builder
    }


    data class ServiceWrapper(val baseUrl: String, val clz: Class<*>) {
        override fun equals(other: Any?): Boolean {
            if (other == null || other !is ServiceWrapper) {
                return false
            }
            return baseUrl == other.baseUrl && clz == other.clz
        }

        override fun hashCode(): Int {
            var result = baseUrl.hashCode()
            result = 31 * result + clz.hashCode()
            return result
        }
    }
}
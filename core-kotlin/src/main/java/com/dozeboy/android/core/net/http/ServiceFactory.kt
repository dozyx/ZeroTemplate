package com.dozeboy.android.core.net.http

import com.dozeboy.android.core.utli.util.Util
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author timon
 * @date 2018/11/21
 */

object ServiceFactory {

    private const val TIME_OUT_CONNECT = 30L
    private const val TIME_OUT_READ = 30L
    private val serviceCache: HashMap<ServiceWrapper, Any> = HashMap()
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()

    init {
        loggingInterceptor.level = 
                if (Util.isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> createService(clazz: Class<T>, baseUrl: String, interceptor: Interceptor? = null): T {
        val serviceWrapper = ServiceWrapper(clazz, baseUrl, interceptor)
        var service = serviceCache[serviceWrapper]
        if (service == null) {
            val builder = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            if (interceptor != null) {
                builder.client(getClientBuilder().addInterceptor(interceptor).build())
            }
            service = builder.build().create(clazz)
            serviceCache.put(serviceWrapper, service!!)
        }
        return service as T
    }

    private fun getClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_READ, TimeUnit.SECONDS).addInterceptor(loggingInterceptor)
    }


}

data class ServiceWrapper(val clazz: Class<*>, val baseUrl: String, val interceptor: Interceptor?)

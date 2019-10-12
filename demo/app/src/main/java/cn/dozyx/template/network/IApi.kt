package cn.dozyx.template.network


import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 * Create by dozyx on 2019/7/24
 */
interface IApi {
    @GET("http://www.cnbeta.com/")
    fun getCnBetaHome(): Observable<ResponseBody>

    @GET("http://gank.io/api/xiandu/categories")
    fun getCategories(): Observable<String>
}

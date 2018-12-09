package ir.mrahimy.ingress.portal.net

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.loopj.android.http.SyncHttpClient

object PortalRestClient {
    //private static final String BASE_URL = "https://api.oportalr.ir/v1/";
    private val BASE_URL = "http://192.168.1.150/portal/index.php/api/v1/portal/"
    private val syncClient = SyncHttpClient()
    private val asyncClient = AsyncHttpClient()

    init {
        asyncClient.addHeader("X-API-KEY", "b2fe82e0-3b13-11e8-b49c-18037385fd0e")
        syncClient.addHeader("X-API-KEY", "b2fe82e0-3b13-11e8-b49c-18037385fd0e")
    }

    fun getSync(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        syncClient.get(getAbsoluteUrl(url), params, responseHandler)
    }

    fun postSync(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        syncClient.post(getAbsoluteUrl(url), params, responseHandler)
    }

    fun getAsync(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        asyncClient.get(getAbsoluteUrl(url), params, responseHandler)
    }

    fun postAsync(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        asyncClient.post(getAbsoluteUrl(url), params, responseHandler)
    }

    private fun getAbsoluteUrl(relativeUrl: String): String {
        return BASE_URL + relativeUrl
    }
}

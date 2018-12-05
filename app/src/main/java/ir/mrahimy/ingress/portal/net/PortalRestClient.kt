package ir.mrahimy.ingress.portal.net

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams

object PortalRestClient {
    //private static final String BASE_URL = "https://api.oportalr.ir/v1/";
    private val BASE_URL = "http://192.168.1.150/oportalr/v1/"

    private val client = AsyncHttpClient()

    fun get(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler)
    }

    fun post(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler)
    }

    private fun getAbsoluteUrl(relativeUrl: String): String {
        return BASE_URL + relativeUrl
    }
}

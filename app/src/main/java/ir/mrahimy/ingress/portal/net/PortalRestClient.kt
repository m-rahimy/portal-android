package ir.mrahimy.ingress.portal.net

import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.loopj.android.http.SyncHttpClient

object PortalRestClient {
    //private static final String BASE_URL = "http://api.oportalr.ir/v1/";
    private val BASE_URL = "http://192.168.1.150/portal/index.php/api/v1/portal/"
    private val syncClient = SyncHttpClient()
    private val asyncClient = AsyncHttpClient()
    public val IMAGE_PATH_BASE = "http://www.oportalr.ir/"
    public val IMAGE_PATH_EMPTY = "http://www.oportalr.ir/img/empty.png"

    private val VAL_LAT = "VAL_LAT"
    private val VAL_LON = "VAL_LON"
    private val rgc = "https://map.ir/reverse?lat=VAL_LAT&lon=VAL_LON"

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

    fun getAddressForPoint(lat: String, lon: String, responseHandler: AsyncHttpResponseHandler) {
        asyncClient.get(rgc.replace(VAL_LAT, lat).replace(VAL_LON, lon),
                responseHandler)
    }

    private fun getAbsoluteUrl(relativeUrl: String): String {
        return BASE_URL + relativeUrl
    }
}

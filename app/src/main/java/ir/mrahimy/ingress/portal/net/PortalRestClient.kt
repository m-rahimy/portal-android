package ir.mrahimy.ingress.portal.net

import android.util.Log
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import com.loopj.android.http.SyncHttpClient
import ir.mrahimy.ingress.portal.view.fragments.AddPortalFragment.Companion.TAG

object PortalRestClient {
    private val BASE_URL = "http://api.oportalr.ir/index.php/api/"
    //private val BASE_URL = "http://192.168.1.150/portal/index.php/api/"
    private val VERSION = "1"
    private val PORTAL_BASE_URL = "${BASE_URL}v$VERSION/portal/"

    private val syncClient = SyncHttpClient()
    private val asyncClient = AsyncHttpClient()
    public val IMAGE_PATH_BASE = "http://www.oportalr.ir/"
    public val IMAGE_PATH_EMPTY = "http://www.oportalr.ir/img/empty.png"
    public val IMAGE_UPLOAD_PATH = "${BASE_URL}v$VERSION/uploader/image"

    private val VAL_LAT = "VAL_LAT"
    private val VAL_LON = "VAL_LON"
    private val rgc = "https://map.ir/reverse?lat=VAL_LAT&lon=VAL_LON"

    init {
        asyncClient.addHeader("X-API-KEY", "b2fe82e0-3b13-11e8-b49c-18037385fd0e")
        syncClient.addHeader("X-API-KEY", "b2fe82e0-3b13-11e8-b49c-18037385fd0e")
    }

    fun getSync(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        syncClient.get(getPortalAbsoluteUrl(url), params, responseHandler)
    }

    fun postSync(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        syncClient.post(getPortalAbsoluteUrl(url), params, responseHandler)
    }

    fun putSync(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        syncClient.put(getPortalAbsoluteUrl(url), params, responseHandler)
    }

    fun getAsync(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        asyncClient.get(getPortalAbsoluteUrl(url), params, responseHandler)
    }

    fun postAsync(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        asyncClient.post(getPortalAbsoluteUrl(url), params, responseHandler)
    }

    fun putAsync(url: String, params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        asyncClient.put(getPortalAbsoluteUrl(url), params, responseHandler)
    }

    fun uploadImage(params: RequestParams, responseHandler: AsyncHttpResponseHandler) {
        Log.d("UPLOAD_DEBUG", "path is $IMAGE_UPLOAD_PATH")
        asyncClient.post(IMAGE_UPLOAD_PATH, params, responseHandler)
    }

    fun getAddressForPoint(lat: String, lon: String, responseHandler: AsyncHttpResponseHandler) {
        asyncClient.get(rgc.replace(VAL_LAT, lat).replace(VAL_LON, lon),
                responseHandler)
    }

    private fun getPortalAbsoluteUrl(relativeUrl: String): String {
        return PORTAL_BASE_URL + relativeUrl
    }
}

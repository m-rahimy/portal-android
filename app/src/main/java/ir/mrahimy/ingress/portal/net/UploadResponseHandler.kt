package ir.mrahimy.ingress.portal.net

import android.util.Log
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class UploadResponseHandler(private val portalImageIds: MutableList<String?>,
                            private val successPredicate: () -> Unit,
                            private val failurePredicate: () -> Unit) : JsonHttpResponseHandler() {
    init {
        Log.d("UPLOAD_DEBUG", "init{}")

    }
    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
        super.onSuccess(statusCode, headers, response)
        portalImageIds.add(response?.optString("filename"))
        Log.d("UPLOAD_DEBUG", "successPredicate")
        successPredicate()
    }

    override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
        super.onFailure(statusCode, headers, responseString, throwable)
        Log.d("UPLOAD_DEBUG", "failurePredicate")
        failurePredicate()
    }

    companion object {
        val TAG = UploadResponseHandler::class.java.simpleName
    }
}
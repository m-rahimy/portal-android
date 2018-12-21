package ir.mrahimy.ingress.portal.net

import android.util.Log
import android.widget.TextView
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import ir.mrahimy.ingress.portal.view.fragments.AddPortalFragment
import org.json.JSONObject

class GetAddressHandler(private val addressTxt: TextView) : JsonHttpResponseHandler() {
    companion object {
        val TAG = GetAddressHandler::class.java.simpleName
    }

    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
        super.onSuccess(statusCode, headers, response)
        addressTxt.text = response?.optString("address")
    }

    override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
        super.onFailure(statusCode, headers, responseString, throwable)
        Log.d(TAG, responseString)
    }
}
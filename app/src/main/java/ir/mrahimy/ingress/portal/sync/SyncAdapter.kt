package ir.mrahimy.ingress.portal.sync

import android.accounts.Account
import android.content.*
import android.database.Cursor
import android.os.Bundle
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import ir.mrahimy.ingress.portal.model.Portal
import ir.mrahimy.ingress.portal.net.PortalRestClient
import timber.log.Timber
import org.json.JSONArray


/**
 * This is used by the Android framework to perform synchronization. IMPORTANT: do NOT create
 * new Threads to perform logic, Android will do this for you; hence, the name.
 *
 *
 * The goal here to perform synchronization, is to do it efficiently as possible. We use some
 * ContentProvider features to batch our writes to the local data source. Be sure to handle all
 * possible exceptions accordingly; random crashes is not a good user-experience.
 */
class SyncAdapter @JvmOverloads constructor(context: Context, autoInitialize: Boolean, allowParallelSyncs: Boolean = false) : AbstractThreadedSyncAdapter(context, autoInitialize, allowParallelSyncs) {
    private val contentResolver: ContentResolver = context.contentResolver

    override fun onPerformSync(account: Account, bundle: Bundle, s: String, contentProviderClient: ContentProviderClient, syncResult: SyncResult) {
        sync(syncResult)
    }

    private fun sync(syncResult: SyncResult) {
        syncPortals(syncResult)
        syncImageUrls(syncResult)
        syncIngressUser(syncResult)
        syncPortalJuncLoc(syncResult)
        syncLikes(syncResult)
        syncImages(syncResult)
        syncPortalLocation(syncResult)
        syncPortalReport(syncResult)
    }

    private fun syncPortalReport(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncPortalLocation(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncImages(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncLikes(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncPortalJuncLoc(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncIngressUser(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncImageUrls(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    private fun syncPortals(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val nets = mutableMapOf<String, Portal>()
        PortalRestClient.get(PortalContract.PATH_PORTALS, RequestParams(), object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("syncACTIONS: $response")
                for (i in 0 until response!!.length()) {
                    val portal = Portal.parse(response.optJSONObject(i))
                    nets[portal.id!!] = portal
                }
                val batch = mutableListOf<ContentProviderOperation>()
                val c = contentResolver.query(PortalContract.Portal.CONTENT_URI, null, null, null, null)
                c?.moveToFirst()

                val id =""
                val id =""
                val id =""
                val id =""
                val id =""
                val id =""

            }
        })
    }

    companion object {
        private val TAG = SyncAdapter::class.java.simpleName

        fun performSync() {
            val b = Bundle()
            b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true)
            b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true)
            ContentResolver.requestSync(AccountGeneral.account, PortalContract.CONTENT_AUTHORITY, b)

        }
    }
}
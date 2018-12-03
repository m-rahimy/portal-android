package ir.mrahimy.ingress.portal.sync

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.ContentResolver
import android.content.Context
import android.content.SyncResult
import android.os.Bundle

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
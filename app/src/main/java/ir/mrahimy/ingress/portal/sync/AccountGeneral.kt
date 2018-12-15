package ir.mrahimy.ingress.portal.sync

import android.accounts.Account
import android.accounts.AccountManager
import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.util.Log

object AccountGeneral {
    /**
     * This is the type of account we are using. i.e. we can specify our app or apps
     * to have different types, such as 'read-only', 'sync-only', & 'admin'.
     */
    private val ACCOUNT_TYPE = "ir.mrahimy.ingress.portal.syncaccount"

    /**
     * This is the name that appears in the Android 'Accounts' settings.
     */
    private val ACCOUNT_NAME = "Portal Sync"


    /**
     * Gets the standard sync account for our app.
     * @return [Account]
     */
    val account: Account
        get() = Account(ACCOUNT_NAME, ACCOUNT_TYPE)

    /**
     * Creates the standard sync account for our app.
     * @param c [Context]
     */
    fun createSyncAccount(c: Context) {
        // Flag to determine if this is a new account or not
        var created = false

        // Get an account and the account manager
        val account = account
        val manager = c.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager

        // Attempt to explicitly create the account with no password or extra data
        if (manager.addAccountExplicitly(account, null, null)) {
            val AUTHORITY = PortalContract.CONTENT_AUTHORITY
            val SYNC_FREQUENCY = (60 * 60).toLong() // 1 hour (seconds)

            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(account, AUTHORITY, 1)

            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(account, AUTHORITY, true)

            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(account, AUTHORITY, Bundle(), SYNC_FREQUENCY)

            created = true
        }

        // Force a sync if the account was just created
        if (created) {
            Log.d("SyncAdapter:"," performSync")
            SyncAdapter.performSync()
        }
    }
}

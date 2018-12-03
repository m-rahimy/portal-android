package ir.mrahimy.ingress.portal.sync

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.NetworkErrorException
import android.content.Context
import android.os.Bundle

/**
 * This is stubbed because we don't need any authentication to access the pretend RSS feed.
 */
class AccountAuthenticator(c: Context) : AbstractAccountAuthenticator(c) {

    @Throws(NetworkErrorException::class)
    override fun addAccount(response: AccountAuthenticatorResponse, accountType: String, authTokenType: String, requiredFeatures: Array<String>, options: Bundle): Bundle? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun confirmCredentials(response: AccountAuthenticatorResponse, account: Account, options: Bundle): Bundle? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun getAuthToken(response: AccountAuthenticatorResponse, account: Account, authTokenType: String, options: Bundle): Bundle? {
        return null
    }

    override fun getAuthTokenLabel(authTokenType: String): String? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun updateCredentials(response: AccountAuthenticatorResponse, account: Account, authTokenType: String, options: Bundle): Bundle? {
        return null
    }

    override fun editProperties(response: AccountAuthenticatorResponse, accountType: String): Bundle? {
        return null
    }

    @Throws(NetworkErrorException::class)
    override fun hasFeatures(response: AccountAuthenticatorResponse, account: Account, features: Array<String>): Bundle? {
        return null
    }
}
package ir.mrahimy.ingress.portal.view

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.net.PortalRestClient
import ir.mrahimy.ingress.portal.sync.AccountGeneral
import ir.mrahimy.ingress.portal.sync.PortalContract
import ir.mrahimy.ingress.portal.sync.SyncAdapter
import ir.mrahimy.ingress.portal.view.fragments.AddPortalFragment
import ir.mrahimy.ingress.portal.view.fragments.HomeFragment
import ir.mrahimy.ingress.portal.view.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import timber.log.Timber

class MainActivity : AppCompatActivity(),
        HomeFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        AddPortalFragment.OnFragmentInteractionListener {

    val TAG = javaClass.simpleName
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //todo: portals list

                PortalRestClient.getAsync(PortalContract.PATH_portal_report, RequestParams(), object : JsonHttpResponseHandler() {
                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                        super.onSuccess(statusCode, headers, response)
                        Timber.d("$TAG onSuccess, $response")
                    }

                    override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                        super.onFailure(statusCode, headers, responseString, throwable)
                        Timber.d("$TAG onFailure, $responseString")

                    }
                })

                goto(HomeFragment.newInstance("A", "B"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add_portal -> {
                goto(AddPortalFragment.newInstance("A", "B"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                goto(SettingsFragment.newInstance("A", "B"))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("SyncAdapter: before createSyncAccount")

        // Create your sync account
        AccountGeneral.createSyncAccount(this)

        // Perform a manual sync by calling this:
        SyncAdapter.performSync()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        goto(HomeFragment.newInstance("A", "B"))

    }

    override fun onResume() {
        super.onResume()
        //TODO: refresh and load data
    }

    fun goto(fragment: Fragment) {
        val b = supportFragmentManager.beginTransaction()
        b.replace(R.id.main_fragment_layout, fragment)
        b.commit()
    }
}

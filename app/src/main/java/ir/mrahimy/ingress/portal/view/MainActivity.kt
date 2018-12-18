package ir.mrahimy.ingress.portal.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.model.PortalImage
import ir.mrahimy.ingress.portal.model.PortalJuncLocation
import ir.mrahimy.ingress.portal.model.PortalLocation
import ir.mrahimy.ingress.portal.net.PortalRestClient
import ir.mrahimy.ingress.portal.sync.AccountGeneral
import ir.mrahimy.ingress.portal.sync.PortalContract
import ir.mrahimy.ingress.portal.sync.SyncAdapter
import ir.mrahimy.ingress.portal.util.toParcelableArray
import ir.mrahimy.ingress.portal.view.fragments.AddPortalFragment
import ir.mrahimy.ingress.portal.view.fragments.HomeFragment
import ir.mrahimy.ingress.portal.view.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

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
                goto(HomeFragment.newInstance("A", "B"))
                title = HomeFragment.TITLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add_portal -> {
                goto(AddPortalFragment.newInstance("A", "B"))
                title = AddPortalFragment.TITLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                goto(SettingsFragment.newInstance("A", "B"))
                title = SettingsFragment.TITLE
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("SyncAdapter:", " before createSyncAccount")

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

    fun showSyncNeededDialog() {
        AlertDialog.Builder(this).setTitle("sync").setMessage("sync?").create().show()
    }

    fun goToImagesActivity(imageUrls: List<PortalImage>?) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            Toast.makeText(this, "No image", Toast.LENGTH_LONG).show()
        } else {
            val i = Intent(this, ImagesViewActivity::class.java)
            i.putExtra("portal_images", imageUrls.toParcelableArray())
            startActivity(i)
        }
    }

    fun goToLocationActivity(locations: List<PortalJuncLocation>?) {
        val i = Intent(this, LocationsActivity::class.java)
        i.putExtra("portal_locations", locations?.toParcelableArray())
        startActivity(i)
    }

    fun goToStaticLocationActivity(locations: List<PortalJuncLocation>?) {
        val i = Intent(this, StaticImageActivity::class.java)
        i.putExtra("portal_locations", locations?.toParcelableArray())
        startActivity(i)
    }
}

package ir.mrahimy.ingress.portal.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.model.ParcelablePortalJuncLocation
import kotlinx.android.synthetic.main.activity_locations.*

class LocationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        val locs = intent?.extras?.getParcelableArray("portal_locations")?.toList()
        val locations = mutableListOf<ParcelablePortalJuncLocation>()
        locs?.forEach {
            locations.add(it as ParcelablePortalJuncLocation)
            val i2 = it.copy(lat = 35.5262, lon = 51.95)
            locations.add(i2)
        }

        //setup rec
        locations_rec_view.adapter = LocationsAdapter(this, locations)
        locations_rec_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
    }

    fun goToLocationActivity(location: ParcelablePortalJuncLocation) {
        val i = Intent(this, LocationActivity::class.java)
        i.putExtra("location", location)
        //TODO: maybe startActivityForResult
        startActivity(i)
    }
}

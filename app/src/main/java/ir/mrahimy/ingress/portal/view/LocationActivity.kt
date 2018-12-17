package ir.mrahimy.ingress.portal.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.model.ParcelablePortalJuncLocation
import ir.mrahimy.ingress.portal.util.simpleSetup
import kotlinx.android.synthetic.main.activity_location.*
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay


class LocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        val loc = intent?.extras?.getParcelable<ParcelablePortalJuncLocation>("location")
        simpleSetupMap(loc?.lat, loc?.lon)

    }

    override fun onResume() {
        super.onResume()
        //setupMap(intent?.extras?.getParcelable("location"))
    }


    private fun simpleSetupMap(lat: Double?, lon: Double?) {
        val point = GeoPoint(lat!!, lon!!)
        map.simpleSetup(this, point, 15.0, false,
                false,false,true,30)
        //item marker
        val m = Marker(map)
        m.position = GeoPoint(lat, lon)
        m.icon = resources.getDrawable(R.drawable.portal_red_trns_96)
        m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(m)
    }
}

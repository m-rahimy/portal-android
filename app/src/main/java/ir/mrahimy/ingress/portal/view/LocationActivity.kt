package ir.mrahimy.ingress.portal.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.model.ParcelablePortalJuncLocation
import kotlinx.android.synthetic.main.activity_location.*
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class LocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        setupMap(intent?.extras?.getParcelable("location"))

    }

    override fun onResume() {
        super.onResume()
        //setupMap(intent?.extras?.getParcelable("location"))
    }


    private fun setupMap(portal: ParcelablePortalJuncLocation?) {
        map.setMultiTouchControls(true)
        map.controller.setZoom(13.0)
        map.controller.setCenter(GeoPoint(portal?.lat!!, portal?.lon!!))

        //user loc
        val loc = MyLocationNewOverlay(GpsMyLocationProvider(this), map)
        loc.enableMyLocation()
        map.overlays.add(loc)

        val scaleBar = ScaleBarOverlay(map)
        scaleBar.setCentred(true)
        val dm = resources.displayMetrics
        scaleBar.setScaleBarOffset(dm.widthPixels / 2, 10)
        map.overlays.add(scaleBar)

        //item marker
        val m = Marker(map)
        m.position = GeoPoint(portal.lat!!, portal.lon!!)
        m.icon = resources.getDrawable(R.mipmap.ic_launcher)
        m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.overlays.add(m)

    }
}

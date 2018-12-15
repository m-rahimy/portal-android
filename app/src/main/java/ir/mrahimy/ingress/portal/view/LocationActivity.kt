package ir.mrahimy.ingress.portal.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ir.map.sdk_common.MaptexLatLng
import ir.map.sdk_map.wrapper.MaptexCameraUpdateFactory
import ir.mrahimy.ingress.portal.R
import ir.map.sdk_map.wrapper.MaptexMap
import ir.map.sdk_map.wrapper.SupportMaptexFragment
import ir.mrahimy.ingress.portal.model.ParcelablePortalJuncLocation


class LocationActivity : AppCompatActivity() {

    private var mMap: MaptexMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        setUpMapIfNeeded(intent?.extras
                ?.getParcelable<ParcelablePortalJuncLocation>("location"))
    }

    override fun onResume() {
        super.onResume()
        setUpMapIfNeeded(intent?.extras?.getParcelable<ParcelablePortalJuncLocation>("location"))
    }

    private fun setUpMapIfNeeded(parcel: ParcelablePortalJuncLocation?) {
        if (mMap == null) {
            (supportFragmentManager.findFragmentById(R.id.portal_loc_map) as SupportMaptexFragment)
                    .getMaptexAsync {
                        mMap = it
                        if (mMap != null) {
                            setupMap(parcel?.lat, parcel?.lon)
                        }
                    }

        }
    }

    private fun setupMap(lat: Double?, lon: Double?) {
        mMap?.moveCamera(MaptexCameraUpdateFactory.newLatLngZoom(
                MaptexLatLng(lat!!, lon!!), 12.0f
        ))

    }
}

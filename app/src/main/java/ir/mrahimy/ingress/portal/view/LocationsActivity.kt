package ir.mrahimy.ingress.portal.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ir.map.sdk_map.wrapper.MaptexMap
import ir.map.sdk_services.models.MaptexError
import ir.map.sdk_services.models.MaptexRouteResponse
import ir.map.sdk_services.models.base.ResponseListener
import ir.mrahimy.ingress.portal.R
import timber.log.Timber
import ir.map.sdk_map.wrapper.OnMaptexReadyCallback
import ir.map.sdk_map.wrapper.SupportMaptexFragment
import ir.map.sdk_services.RouteMode
import ir.map.sdk_services.ServiceHelper
import ir.map.sdk_common.MaptexLatLng
import ir.map.sdk_map.wrapper.MaptexCameraUpdateFactory


class LocationsActivity : AppCompatActivity(), ResponseListener<MaptexRouteResponse> {
    private var mMap: MaptexMap? = null

    override fun onError(error: MaptexError?) {
        Log.e("Error in Route : ", error?.message)
    }

    override fun onSuccess(response: MaptexRouteResponse?) {
        //updateRoutingInfo(response);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        intent.getParcelableArrayExtra("portal_locations").forEach {
            Timber.d(it.toString())
        }
        //setUpMapIfNeeded()
    }

    override fun onResume() {
        super.onResume()
        //setUpMapIfNeeded()
    }

    /*private fun setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null)
        // Try to obtain the map wrap the SupportMapFragment.
            (supportFragmentManager.findFragmentById(R.id.myMapView) as SupportMaptexFragment)
                    .getMaptexAsync { var1 ->
                        mMap = var1
                        if (mMap != null)
                        // Checks if we were successful in obtaining the map
                            setUpMap()
                    }
    }*/

    private fun setUpMap() {
        mMap?.moveCamera(MaptexCameraUpdateFactory.newLatLngZoom(MaptexLatLng(35.6964895, 51.279745), 12f))
        val latLng1 = MaptexLatLng(35.7413024, 51.421966)
        val latLng2 = MaptexLatLng(35.7411668, 51.4261612)
        ServiceHelper().getRouteInfo(latLng1, latLng2, RouteMode.BASIC, this)
    }
}

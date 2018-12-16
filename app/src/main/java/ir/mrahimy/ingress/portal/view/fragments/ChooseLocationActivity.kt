package ir.mrahimy.ingress.portal.view.fragments

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.util.simpleSetup
import kotlinx.android.synthetic.main.activity_choose_location.*
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.api.IGeoPoint
import android.util.DisplayMetrics
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.views.overlay.*


class ChooseLocationActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName
    val items = ArrayList<OverlayItem>()
    var zoom:Double? = 5.5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_location)
        title = "Choose Locations"

        var lat = intent?.extras?.getDouble("lat", 31.094)
        var lon = intent?.extras?.getDouble("lon", 53.485)
        zoom = intent?.extras?.getDouble("zoom", 5.5)
        if (lat == null || lat == 0.0) lat = 31.094
        if (lon == null || lon == 0.0) lon = 53.485
        if (zoom == null) zoom = 5.5
        // center marker (same as snapp)
        SimpleSetupMap(lat, lon)

        //setupMapTouch(items)

        choose_location_choose_button.setOnClickListener {
            if (items.isNotEmpty()) {
                finishTheActivity()
            } else {
                Toast.makeText(this@ChooseLocationActivity, "No loc",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun finishTheActivity() {
        val i = Intent()
        i.putExtra("lat", items.get(0).point.latitude)
        i.putExtra("lon", items.get(0).point.longitude)
        i.putExtra("zoom", zoom)
        setResult(Activity.RESULT_OK, i)
        finish()
    }

    private fun SimpleSetupMap(lat: Double, lon: Double) {
        choose_location_map.simpleSetup(this, GeoPoint(lat, lon),
                zoom, false, false,
                false, true, 30)

        //trying center marker
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        choose_location_map.addMapListener(object : MapListener {
            override fun onZoom(event: ZoomEvent?): Boolean {
                val pt = choose_location_map.getProjection().fromPixels(width / 2, height / 2)
                //--- Create Overlay
                repositionMarker(pt)
                Log.d(TAG, "zoomed to ${event?.zoomLevel}")
                zoom = event?.zoomLevel!!
                return false
            }

            override fun onScroll(event: ScrollEvent?): Boolean {
                val pt = choose_location_map.getProjection().fromPixels(width / 2, height / 2)
                //--- Create Overlay
                repositionMarker(pt)
                //Log.d(TAG, "scrolled to ${event?.}")
                return false
            }
        })
    }

    private fun repositionMarker(pt: IGeoPoint?) {
        val m = Marker(choose_location_map)
        m.position = pt as GeoPoint?
        m.icon = resources.getDrawable(R.mipmap.ic_launcher) //TODO change to portal animated
        m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        choose_location_map.overlays.clear()
        choose_location_map.overlays.add(m)
        items.clear()
        items.add(OverlayItem("", "", pt))
    }

    private fun setupMapTouch(items: ArrayList<OverlayItem>) {

        val mReceive = object : MapEventsReceiver {
            override fun longPressHelper(p: GeoPoint?): Boolean {
                Log.d(TAG, "longPressHelper")
                //setMarker(p, items)
                return false
            }

            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                //setMarker(p, items)
                return false
            }
        }

        val eventsOverlay = MapEventsOverlay(mReceive)
        choose_location_map.overlays.add(eventsOverlay)

        val overlay = ItemizedOverlayWithFocus<OverlayItem>(this, items,
                object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                    override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                        Log.d(TAG, item?.title)
                        return false
                    }

                    override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {

                        return true
                    }
                })

        overlay.setFocusItemsOnTap(true)
        choose_location_map.overlays.add(overlay)
    }

    private fun setMarker(p: GeoPoint?, items: ArrayList<OverlayItem>) {
        items.clear()
        items.add(OverlayItem("Location", "Portal Location", p))
        val overlay = ItemizedOverlayWithFocus<OverlayItem>(this, items,
                object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                    override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                        Log.d(TAG, item?.title)
                        return false
                    }

                    override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {

                        return true
                    }
                })
        overlay.setFocusItemsOnTap(true)
        choose_location_map.overlays.clear()
        choose_location_map.overlays.add(overlay)
        choose_location_map.invalidate()
    }

    override fun onBackPressed() {
        finishTheActivity()
        //super.onBackPressed()
    }

}

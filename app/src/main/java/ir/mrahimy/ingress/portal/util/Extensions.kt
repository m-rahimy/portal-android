package ir.mrahimy.ingress.portal.util

import android.content.ContentResolver
import android.content.Context
import ir.mrahimy.ingress.portal.dbmodel.*
import ir.mrahimy.ingress.portal.model.*
import ir.mrahimy.ingress.portal.sync.PortalContract
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.text.SimpleDateFormat
import java.util.*

fun List<DbPortal>.getFullData(contentResolver: ContentResolver): List<Portal> {
    val res = mutableListOf<Portal>()
    var cursor = contentResolver.query(PortalContract.IngressUser.CONTENT_URI, null, null, null, null)
    cursor.moveToFirst()
    val dbIngressUserList = DbIngressUser.parseAll(cursor)

    cursor = contentResolver.query(PortalContract.PortalJuncLocation.CONTENT_URI, null, null, null, null)
    cursor.moveToFirst()
    val dbPortalJuncLocationList = DbPortalJuncLocation.parseAll(cursor)

    cursor = contentResolver.query(PortalContract.PortalLike.CONTENT_URI, null, null, null, null)
    cursor.moveToFirst()
    val dbPortalLikeList = DbPortalLike.parseAll(cursor)

    cursor = contentResolver.query(PortalContract.PortalImage.CONTENT_URI, null, null, null, null)
    cursor.moveToFirst()
    val dbPortalImageList = DbPortalImage.parseAll(cursor)

    cursor = contentResolver.query(PortalContract.PortalLocation.CONTENT_URI, null, null, null, null)
    cursor.moveToFirst()
    val dbPortalLocationList = DbPortalLocation.parseAll(cursor)

    cursor = contentResolver.query(PortalContract.PortalReport.CONTENT_URI, null, null, null, null)
    cursor.moveToFirst()
    val dbPortalReportList = DbPortalReport.parseAll(cursor)

    cursor = contentResolver.query(PortalContract.ImageUrl.CONTENT_URI, null, null, null, null)
    cursor.moveToFirst()
    val dbImageUrlList = DbImageUrl.parseAll(cursor)
    cursor.close()

    this.forEachIndexed { t, it ->
        //Timber.d("FUllData: this is $it and index is $t")
        val dbIngressUserInForEach = DbIngressUser.getByName(dbIngressUserList, it.uploader!!)
        val ingressUser = IngressUser.parse(dbIngressUserInForEach)
        val dbLikes = DbPortalLike.getByPortalID(dbPortalLikeList, it.id!!)
        val likes = PortalLike.parseAll(dbIngressUserList, dbLikes)
        val dbReports = DbPortalReport.getByPortalID(dbPortalReportList, it.id!!)
        val reports = PortalReport.parseAll(dbIngressUserList, dbReports)

        val dbPortalImages = DbPortalImage.getByPortalID(dbPortalImageList, it.id!!)
        val portalImageList = mutableListOf<PortalImage>()
        dbPortalImages.forEachIndexed { index, dbPortalImageL ->
            portalImageList.add(PortalImage.parse(dbPortalImageL, dbImageUrlList, dbIngressUserList))
        }

        val dbPortalJuncLocations = DbPortalJuncLocation.getByPortal(dbPortalJuncLocationList, it.id!!)
        val pjl_List = mutableListOf<PortalJuncLocation>()
        dbPortalJuncLocations.forEachIndexed { index, item ->
            //Timber.d("FATALITY_IOIO_LOOP "+ item.)
            pjl_List.add(PortalJuncLocation.parse(item, dbPortalLocationList, dbIngressUserList))
        }

        val portal = Portal(
                it.id,
                it.title,
                it.description,
                ingressUser,
                likes,
                reports, portalImageList, pjl_List,
                it.inserted_date,
                it.updated_date)

        portal.likes?.forEach {
            it.portal = portal
        }

        portal.reports?.forEach {
            it.portal = portal
        }

        portal.imageUrls?.forEach {
            it.portal = portal
        }

        portal.locations?.forEach {
            it.portal = portal
        }

        res.add(portal)
    }

    return res

}

fun Int.toBoolean() = this == 1
fun Boolean.toInt() = if (this) 1 else 0

fun List<PortalImage>.toParcelableArray(): Array<ParcelablePortalImage> {
    val res = mutableListOf<ParcelablePortalImage>()
    this.forEach {
        res.add(ParcelablePortalImage(it))
    }
    return res.toTypedArray()
}

fun List<PortalJuncLocation>.toParcelableArray(): Array<ParcelablePortalJuncLocation> {
    val res = mutableListOf<ParcelablePortalJuncLocation>()
    this.forEach {
        res.add(ParcelablePortalJuncLocation(it))
    }
    return res.toTypedArray()
}

fun MapView.simpleSetup(context: Context, center: GeoPoint, zoom: Double?,
                        isRotationEnabled: Boolean,
                        centerToSelfLocation: Boolean,
                        isSelfLocEnabled: Boolean,
                        isCompassEnabled: Boolean,
                        scaleBarY: Int) {
    this.setMultiTouchControls(true)
    val zoom0 = zoom ?: 5.5
    this.controller.setZoom(zoom0)
    this.controller.setCenter(center)
    if (isRotationEnabled) {
        val rotation = RotationGestureOverlay(this)
        rotation.isEnabled = true
        this.overlays.add(rotation)
    }

    if (isSelfLocEnabled) {
        val loc = MyLocationNewOverlay(GpsMyLocationProvider(context), this)
        loc.enableMyLocation()
        this.overlays.add(loc)
        if (centerToSelfLocation/* && loc.myLocation != null*/)
            this.controller.setCenter(loc.myLocation)
    }

    val scaleBar = ScaleBarOverlay(this)
    scaleBar.setCentred(true)
    val dm = resources.displayMetrics
    scaleBar.setScaleBarOffset(dm.widthPixels / 2, scaleBarY)
    this.overlays.add(scaleBar)
    if (isCompassEnabled) {
        val compass = CompassOverlay(context, InternalCompassOrientationProvider(context), this);
        compass.enableCompass()
        this.overlays.add(compass)
    }
}

fun Date.toMySqlformat(): String {
    //"2018-12-07 09:43:21"
    val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
    return format.format(this)
}


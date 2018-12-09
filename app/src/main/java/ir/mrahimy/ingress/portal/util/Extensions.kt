package ir.mrahimy.ingress.portal.util

import android.content.ContentResolver
import ir.mrahimy.ingress.portal.dbmodel.*
import ir.mrahimy.ingress.portal.model.IngressUser
import ir.mrahimy.ingress.portal.model.Portal
import ir.mrahimy.ingress.portal.model.PortalLike
import ir.mrahimy.ingress.portal.model.PortalReport
import ir.mrahimy.ingress.portal.sync.PortalContract

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
    cursor.close()

    this.forEachIndexed { t, it ->
        val dbIngressUserI = DbIngressUser.getByName(dbIngressUserList, it.uploader!!)
        val ingressUser = IngressUser.parse(dbIngressUserI)
        val dbLikes = DbPortalLike.getByPortalID(dbPortalLikeList, it.id!!)
        val likes = PortalLike.parseAll(dbIngressUserList, dbLikes)
        val dbReports = DbPortalReport.getByPortalID(dbPortalReportList, it.id!!)
        val reports = PortalReport.parseAll(dbIngressUserList, dbReports)

        val portal = Portal(
                it.id,
                it.title,
                it.description,
                ingressUser,
                likes,
                reports, null, null,
                it.inserted_date,
                it.updated_date)

        portal.likes?.forEach {
            it.portal = portal
        }

        portal.reports?.forEach {
            it.portal = portal
        }

        res.add(portal)
    }

    return res

}
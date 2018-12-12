package ir.mrahimy.ingress.portal.dbmodel

import android.database.Cursor
import ir.mrahimy.ingress.portal.sync.PortalContract
import ir.mrahimy.ingress.portal.util.toBoolean
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

data class DbPortal(
        var id: String? = "",
        var title: String? = "",
        var description: String? = "",
        var uploader: String? = "",
        var inserted_date: String? = "13971213124532",
        var updated_date: String? = "13971213124532"
) {
    companion object {
        fun parse(jsonObject: JSONObject): DbPortal {
            val portal = DbPortal()
            portal.id = jsonObject.optString("id")
            portal.title = jsonObject.optString("title")
            portal.description = jsonObject.optString("description")
            portal.uploader = jsonObject.optString("uploader")
            portal.inserted_date = jsonObject.optString("inserted_date")
            portal.updated_date = jsonObject.optString("updated_date")
            return portal
        }

        fun parseAll(jsonArray: JSONArray): List<DbPortal> {
            val res = mutableListOf<DbPortal>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }

        fun parse(cursor: Cursor): DbPortal {
            val portal = DbPortal()
            portal.id = cursor.getString(cursor.getColumnIndex(PortalContract.Portal.COL_id))
            portal.title = cursor.getString(cursor.getColumnIndex(PortalContract.Portal.COL_title))
            portal.description = cursor.getString(cursor.getColumnIndex(PortalContract.Portal.COL_description))
            portal.uploader = cursor.getString(cursor.getColumnIndex(PortalContract.Portal.COL_uploader))
            portal.inserted_date = cursor.getString(cursor.getColumnIndex(PortalContract.Portal.COL_inserted_date))
            portal.updated_date = cursor.getString(cursor.getColumnIndex(PortalContract.Portal.COL_updated_date))
            return portal
        }

        fun parseAll(cursor: Cursor): List<DbPortal> {
            val res = mutableListOf<DbPortal>()
            do {
                res.add(parse(cursor))
            } while (cursor.moveToNext())

            return res
        }

        fun getByID(list: List<DbPortal>, id: String): DbPortal {
            return list.filter { it.id == id }[0]
        }

        fun getByID(c: Cursor, id: String): DbPortal {
            return getByID(parseAll(c), id)
        }

        fun getByUploader(list: List<DbPortal>, upName: String): List<DbPortal> {
            return list.filter { it.uploader == upName }
        }

        fun getByUploader(c: Cursor, id: String): List<DbPortal> {
            return getByUploader(parseAll(c), id)
        }
    }
}

data class DbIngressUser(
        var name: String? = "...",
        var email: String? = "...",
        var inserted_date: String? = "13971213124532",
        var updated_date: String? = "13971213124532"
) {
    companion object {
        fun parse(jsonUser: JSONObject): DbIngressUser {
            val user = DbIngressUser()
            user.name = jsonUser.optString("name")
            user.email = jsonUser.optString("email")
            user.inserted_date = jsonUser.optString("inserted_date")
            user.updated_date = jsonUser.optString("updated_date")
            return user
        }

        fun parseAll(userJsonArray: JSONArray): List<DbIngressUser> {
            val res = mutableListOf<DbIngressUser>()
            (0 until userJsonArray.length()).forEach {
                res.add(parse(userJsonArray.optJSONObject(it)))
            }
            return res
        }

        fun parse(cursor: Cursor): DbIngressUser {
            val res = DbIngressUser()
            res.name = cursor.getString(cursor.getColumnIndex(PortalContract.IngressUser.COL_name))
            res.email = cursor.getString(cursor.getColumnIndex(PortalContract.IngressUser.COL_email))
            res.inserted_date = cursor.getString(cursor.getColumnIndex(PortalContract.IngressUser.COL_inserted_date))
            res.updated_date = cursor.getString(cursor.getColumnIndex(PortalContract.IngressUser.COL_updated_date))
            return res
        }

        fun parseAll(cursor: Cursor): List<DbIngressUser> {
            val res = mutableListOf<DbIngressUser>()
            do {
                res.add(parse(cursor))
            } while (cursor.moveToNext())

            return res
        }

        fun getByName(list: List<DbIngressUser>, name: String): DbIngressUser {
            return list.filter { it.name == name }[0]
        }

        fun getByName(c: Cursor, name: String): DbIngressUser {
            return getByName(parseAll(c), name)
        }

    }
}

data class DbImageUrl(
        var url: String? = "",
        var uploader: String? = "",
        var inserted_date: String? = "13971213124532",
        var updated_date: String? = "13971213124532"
) {
    companion object {
        fun parse(jsonObject: JSONObject): DbImageUrl {
            val dbImageUrl = DbImageUrl()
            dbImageUrl.url = jsonObject.optString("url")
            dbImageUrl.uploader = jsonObject.optString("uploader_name")
            dbImageUrl.inserted_date = jsonObject.optString("inserted_date")
            dbImageUrl.updated_date = jsonObject.optString("updated_date")
            return dbImageUrl
        }

        fun parseAll(jsonArray: JSONArray): List<DbImageUrl> {
            val res = mutableListOf<DbImageUrl>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }

            return res
        }

        fun parse(cursor: Cursor): DbImageUrl {
            val res = DbImageUrl()
            res.url = cursor.getString(cursor.getColumnIndex(PortalContract.ImageUrl.COL_url))
            res.uploader = cursor.getString(cursor.getColumnIndex(PortalContract.ImageUrl.COL_uploader))
            res.inserted_date = cursor.getString(cursor.getColumnIndex(PortalContract.ImageUrl.COL_inserted_date))
            res.updated_date = cursor.getString(cursor.getColumnIndex(PortalContract.ImageUrl.COL_updated_date))
            Timber.d("FATALITY000: returning  $res")
            return res
        }

        fun parseAll(cursor: Cursor): List<DbImageUrl> {
            val res = mutableListOf<DbImageUrl>()
            do {
                res.add(parse(cursor))
            } while (cursor.moveToNext())

            return res
        }

        fun getByUrl(list: List<DbImageUrl>, url: String): DbImageUrl {
            return list.filter { it.url == url }[0]
        }

        fun getByUrl(c: Cursor, url: String): DbImageUrl {
            return getByUrl(parseAll(c), url)
        }

        fun getByUploader(list: List<DbImageUrl>, uploaderName: String): List<DbImageUrl> {
            return list.filter { it.uploader == uploaderName }
        }

        fun getByUploader(c: Cursor, uploaderName: String): List<DbImageUrl> {
            return getByUploader(parseAll(c), uploaderName)
        }

    }
}

data class DbPortalLocation(
        var id: String? = "",
        var lat: Double? = 0.0,
        var lon: Double? = 0.0,
        var uploader: String? = "",
        var inserted_date: String? = "13971213124532",
        var updated_date: String? = "13971213124532"
) {
    companion object {
        fun parse(jsonObject: JSONObject): DbPortalLocation {
            val dbPortalLocation = DbPortalLocation()
            dbPortalLocation.id = jsonObject.optString("id")
            dbPortalLocation.lat = jsonObject.optDouble("lat")
            dbPortalLocation.lon = jsonObject.optDouble("lon")
            dbPortalLocation.uploader = jsonObject.optString("uploader_name")
            dbPortalLocation.inserted_date = jsonObject.optString("inserted_date")
            dbPortalLocation.updated_date = jsonObject.optString("updated_date")
            return dbPortalLocation
        }

        fun parseAll(jsonArray: JSONArray): List<DbPortalLocation> {
            val res = mutableListOf<DbPortalLocation>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }

        fun parse(cursor: Cursor): DbPortalLocation {
            val res = DbPortalLocation()
            res.id = cursor.getString(cursor.getColumnIndex(PortalContract.PortalLocation.COL_id))
            res.lat = cursor.getDouble(cursor.getColumnIndex(PortalContract.PortalLocation.COL_lat))
            res.lon = cursor.getDouble(cursor.getColumnIndex(PortalContract.PortalLocation.COL_lon))
            res.uploader = cursor.getString(cursor.getColumnIndex(PortalContract.PortalLocation.COL_uploader_name))
            res.inserted_date = cursor.getString(cursor.getColumnIndex(PortalContract.PortalLocation.COL_inserted_date))
            res.updated_date = cursor.getString(cursor.getColumnIndex(PortalContract.PortalLocation.COL_updated_date))
            return res
        }

        fun parseAll(cursor: Cursor): List<DbPortalLocation> {
            val res = mutableListOf<DbPortalLocation>()
            do {
                res.add(parse(cursor))
            } while (cursor.moveToNext())

            return res
        }

        fun getByID(list: List<DbPortalLocation>, id: String): DbPortalLocation {
            return list.filter { it.id == id }[0]
        }

        fun getByID(c: Cursor, id: String): DbPortalLocation {
            return getByID(parseAll(c), id)
        }

        fun getByUploader(list: List<DbPortalLocation>, uploader: String): List<DbPortalLocation> {
            return list.filter { it.uploader == uploader }
        }

        fun getByUploader(c: Cursor, id: String): List<DbPortalLocation> {
            return getByUploader(parseAll(c), id)
        }
    }
}

//
data class DbPortalJuncLocation(
        var id: String? = "",
        var portal_id: String? = "",
        var location_id: String? = "",
        var inserted_date: String? = "",
        var updated_date: String? = ""
) {
    companion object {

        fun parse(jsonObject: JSONObject): DbPortalJuncLocation {
            val res = DbPortalJuncLocation()
            res.id = jsonObject.optString("id")
            res.portal_id = jsonObject.optString("portal_id")
            res.location_id = jsonObject.optString("location_id")
            res.inserted_date = jsonObject.optString("inserted_date")
            res.updated_date = jsonObject.optString("updated_date")
            return res
        }

        fun parseAll(jsonArray: JSONArray): List<DbPortalJuncLocation> {
            val res = mutableListOf<DbPortalJuncLocation>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }

        fun parse(cursor: Cursor): DbPortalJuncLocation {
            val res = DbPortalJuncLocation()
            res.id = cursor.getString(cursor.getColumnIndex(PortalContract.PortalJuncLocation.COL_id))
            res.portal_id = cursor.getString(cursor.getColumnIndex(PortalContract.PortalJuncLocation.COL_portalID))
            res.location_id = cursor.getString(cursor.getColumnIndex(PortalContract.PortalJuncLocation.COL_locationID))
            res.inserted_date = cursor.getString(cursor.getColumnIndex(PortalContract.PortalJuncLocation.COL_inserted_date))
            res.updated_date = cursor.getString(cursor.getColumnIndex(PortalContract.PortalJuncLocation.COL_updated_date))
            return res
        }

        fun parseAll(cursor: Cursor): List<DbPortalJuncLocation> {
            val res = mutableListOf<DbPortalJuncLocation>()
            do {
                res.add(parse(cursor))
            } while (cursor.moveToNext())

            return res
        }

        fun getByID(list: List<DbPortalJuncLocation>, id: String): DbPortalJuncLocation {
            return list.filter { it.id == id }[0]
        }

        fun getByID(c: Cursor, id: String): DbPortalJuncLocation {
            return parseAll(c).filter { it.id == id }[0]
        }

        fun getByPortal(list: List<DbPortalJuncLocation>, id: String): List<DbPortalJuncLocation> {
            return list.filter { it.portal_id == id }
        }

        fun getByPortal(c: Cursor, id: String): List<DbPortalJuncLocation> {
            return getByPortal(parseAll(c), id)
        }

        fun getByLocation(list: List<DbPortalJuncLocation>, id: String): List<DbPortalJuncLocation> {
            return list.filter { it.location_id == id }
        }

        fun getByLocation(c: Cursor, id: String): List<DbPortalJuncLocation> {
            return getByLocation(parseAll(c), id)
        }
    }
}

data class DbPortalLike(
        var id: String? = "id",
        var portal_id: String? = "",
        var username: String? = "",
        var like: Boolean? = false,
        var inserted_date: String? = "",
        var updated_date: String? = ""
) {
    companion object {
        fun parse(jsonObject: JSONObject): DbPortalLike {
            val res = DbPortalLike()
            res.id = jsonObject.optString("id")
            res.portal_id = jsonObject.optString("portal_id")
            res.username = jsonObject.optString("username")
            res.like = jsonObject.optInt("_like").toBoolean()
            res.inserted_date = jsonObject.optString("inserted_date")
            res.updated_date = jsonObject.optString("updated_date")
            return res
        }

        fun parseAll(jsonArray: JSONArray): List<DbPortalLike> {
            val res = mutableListOf<DbPortalLike>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }

        fun parse(cursor: Cursor): DbPortalLike {
            val res = DbPortalLike()
            res.id = cursor.getString(cursor.getColumnIndex(PortalContract.PortalLike.COL_id))
            res.portal_id = cursor.getString(cursor.getColumnIndex(PortalContract.PortalLike.COL_portalID))
            res.username = cursor.getString(cursor.getColumnIndex(PortalContract.PortalLike.COL_username))
            res.like = cursor.getInt(cursor.getColumnIndex(PortalContract.PortalLike.COL_like)).toBoolean()
            res.inserted_date = cursor.getString(cursor.getColumnIndex(PortalContract.PortalLike.COL_inserted_date))
            res.updated_date = cursor.getString(cursor.getColumnIndex(PortalContract.PortalLike.COL_updated_date))
            return res
        }

        fun parseAll(cursor: Cursor): List<DbPortalLike> {
            val res = mutableListOf<DbPortalLike>()
            do {
                res.add(parse(cursor))
            } while (cursor.moveToNext())

            return res
        }

        fun getByID(list: List<DbPortalLike>, id: String): DbPortalLike {
            return list.filter { it.id == id }[0]
        }

        fun getByID(c: Cursor, id: String): DbPortalLike {
            return getByID(parseAll(c), id)
        }

        fun getByPortalID(list: List<DbPortalLike>, id: String): List<DbPortalLike> {
            return list.filter { it.portal_id == id }
        }

        fun getByPortalID(c: Cursor, id: String): List<DbPortalLike> {
            return getByPortalID(parseAll(c), id)
        }

        fun getByUser(list: List<DbPortalLike>, user: String): List<DbPortalLike> {
            return list.filter { it.username == user }
        }

        fun getByUser(c: Cursor, user: String): List<DbPortalLike> {
            return getByUser(parseAll(c), user)
        }
    }
}

data class DbPortalImage(
        var id: String? = "",
        var portal_id: String? = "",
        var url: String? = "",
        var inserted_date: String? = "",
        var updated_date: String? = ""
) {
    companion object {
        fun parse(jsonObject: JSONObject): DbPortalImage {
            val res = DbPortalImage()
            res.id = jsonObject.optString("id")
            res.portal_id = jsonObject.optString("portal_id")
            res.url = jsonObject.optString("url")
            res.inserted_date = jsonObject.optString("inserted_date")
            res.updated_date = jsonObject.optString("updated_date")
            return res
        }

        fun parseAll(jsonArray: JSONArray): List<DbPortalImage> {
            val res = mutableListOf<DbPortalImage>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }


        fun parse(cursor: Cursor): DbPortalImage {
            val res = DbPortalImage()
            res.id = cursor.getString(cursor.getColumnIndex(PortalContract.PortalImage.COL_id))
            res.portal_id = cursor.getString(cursor.getColumnIndex(PortalContract.PortalImage.COL_portalID))
            res.url = cursor.getString(cursor.getColumnIndex(PortalContract.PortalImage.COL_url))
            res.inserted_date = cursor.getString(cursor.getColumnIndex(PortalContract.PortalImage.COL_inserted_date))
            res.updated_date = cursor.getString(cursor.getColumnIndex(PortalContract.PortalImage.COL_updated_date))
            return res
        }

        fun parseAll(cursor: Cursor): List<DbPortalImage> {
            val res = mutableListOf<DbPortalImage>()
            do {
                res.add(parse(cursor))
            } while (cursor.moveToNext())

            return res
        }

        fun getByID(list: List<DbPortalImage>, id: String): DbPortalImage {
            return list.filter { it.id == id }[0]
        }

        fun getByID(c: Cursor, id: String): DbPortalImage {
            return getByID(parseAll(c), id)
        }

        fun getByPortalID(list: List<DbPortalImage>, id: String): List<DbPortalImage> {
            return list.filter { it.portal_id == id }
        }

        fun getByPortalID(c: Cursor, id: String): List<DbPortalImage> {
            return getByPortalID(parseAll(c), id)
        }

        fun getByUrl(list: List<DbPortalImage>, url: String): List<DbPortalImage> {
            return list.filter { it.url == url }
        }

        fun getByUrl(c: Cursor, url: String): List<DbPortalImage> {
            return getByUrl(parseAll(c), url)
        }
    }
}

data class DbPortalReport(
        var id: String? = "",
        var portal_id: String? = "",
        var description: String? = "",
        var username: String? = "",
        var inserted_date: String? = "",
        var updated_date: String? = ""
) {
    companion object {
        fun parse(jsonObject: JSONObject): DbPortalReport {
            val res = DbPortalReport()
            res.id = jsonObject.optString("id")
            res.portal_id = jsonObject.optString("portal_id")
            res.description = jsonObject.optString("description")
            res.username = jsonObject.optString("username")
            res.inserted_date = jsonObject.optString("inserted_date")
            res.updated_date = jsonObject.optString("updated_date")

            return res
        }

        fun parseAll(jsonArray: JSONArray): List<DbPortalReport> {
            val res = mutableListOf<DbPortalReport>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }


        fun parse(cursor: Cursor): DbPortalReport {
            val res = DbPortalReport()
            res.id = cursor.getString(cursor.getColumnIndex(PortalContract.PortalReport.COL_id))
            res.portal_id = cursor.getString(cursor.getColumnIndex(PortalContract.PortalReport.COL_portal_id))
            res.description = cursor.getString(cursor.getColumnIndex(PortalContract.PortalReport.COL_description))
            res.username = cursor.getString(cursor.getColumnIndex(PortalContract.PortalReport.COL_username))
            res.inserted_date = cursor.getString(cursor.getColumnIndex(PortalContract.PortalReport.COL_inserted_date))
            res.updated_date = cursor.getString(cursor.getColumnIndex(PortalContract.PortalReport.COL_updated_date))
            return res
        }

        fun parseAll(cursor: Cursor): List<DbPortalReport> {
            val res = mutableListOf<DbPortalReport>()
            do {
                res.add(parse(cursor))
            } while (cursor.moveToNext())

            return res
        }

        fun getByID(list: List<DbPortalReport>, id: String): DbPortalReport {
            return list.filter { it.id == id }[0]
        }

        fun getByID(c: Cursor, id: String): DbPortalReport {
            return getByID(parseAll(c), id)
        }

        fun getByPortalID(list: List<DbPortalReport>, id: String): List<DbPortalReport> {
            return list.filter { it.portal_id == id }
        }

        fun getByPortalID(c: Cursor, id: String): List<DbPortalReport> {
            return getByPortalID(parseAll(c), id)
        }

        fun getByUser(list: List<DbPortalReport>, user: String): List<DbPortalReport> {
            return list.filter { it.username == user }
        }

        fun getByUser(c: Cursor, user: String): List<DbPortalReport> {
            return getByPortalID(parseAll(c), user)
        }
    }

}
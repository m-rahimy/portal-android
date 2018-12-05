package ir.mrahimy.ingress.portal.dbmodel

import ir.mrahimy.ingress.portal.sync.PortalContract
import org.json.JSONArray
import org.json.JSONObject

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
            dbImageUrl.uploader = jsonObject.optString("uploader")
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
    }
}

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
    }
}

data class DbPortalLike(
        var id: String? = "id",
        var portal_id: String? = "",
        var username: String? = "",
        var inserted_date: String? = "",
        var updated_date: String? = ""
) {
    companion object {
        fun parse(jsonObject: JSONObject): DbPortalLike {
            val res = DbPortalLike()
            res.id = jsonObject.optString("id")
            res.portal_id = jsonObject.optString("portal_id")
            res.username = jsonObject.optString("username")
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
    }

}
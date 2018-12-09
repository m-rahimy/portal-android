package ir.mrahimy.ingress.portal.model

import ir.mrahimy.ingress.portal.dbmodel.DbIngressUser
import ir.mrahimy.ingress.portal.dbmodel.DbPortalLike
import ir.mrahimy.ingress.portal.dbmodel.DbPortalReport
import org.json.JSONArray
import org.json.JSONObject


/**
 * Created by vincent on 11/23/18.
 */
data class Portal(
        var id: String? = "",
        var title: String? = "",
        var description: String? = "",
        var uploader: IngressUser? = IngressUser(),
        var likes: List<PortalLike>? = listOf(),
        var reports: List<PortalReport>? = listOf(),
        var imageUrls: List<ImageUrl>? = listOf(),
        var locations: List<PortalLocation>? = listOf(),
        var inserted_date: String? = "13971213124532",
        var updated_date: String? = "13971213124532"
) {
    companion object {
        fun parse(jsonPortal: JSONObject): Portal {
            val portal = Portal()
            portal.id = jsonPortal.optString("id")
            portal.title = jsonPortal.optString("title")
            portal.description = jsonPortal.optString("description")
            portal.uploader = IngressUser.parse(jsonPortal.optJSONObject("uploader"))
            portal.likes = PortalLike.parseAll(jsonPortal.optJSONArray("likes"))
            portal.reports = PortalReport.parseAll(jsonPortal.optJSONArray("reports"))
            portal.imageUrls = ImageUrl.parseAll(jsonPortal.optJSONArray("image_urls"))
            portal.locations = PortalLocation.parseAll(jsonPortal.optJSONArray("locations"))
            portal.inserted_date = jsonPortal.optString("inserted_date")
            portal.updated_date = jsonPortal.optString("updated_date")
            return portal
        }

        fun parseAll(jsonArray: JSONArray): List<Portal> {
            val res = mutableListOf<Portal>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }

            return res
        }

    }
}

//
data class IngressUser(
        var name: String? = "...",
        var email: String? = "...",
        var inserted_date: String? = "13971213124532",
        var updated_date: String? = "13971213124532"
) {
    companion object {
        fun parse(jsonUser: JSONObject): IngressUser {
            val user = IngressUser()
            user.name = jsonUser.optString("name")
            user.email = jsonUser.optString("email")
            user.inserted_date = jsonUser.optString("inserted_date")
            user.updated_date = jsonUser.optString("updated_date")
            return user
        }

        fun parseAll(userJsonArray: JSONArray): List<IngressUser> {
            val res = mutableListOf<IngressUser>()
            (0 until userJsonArray.length()).forEach {
                res.add(IngressUser.parse(userJsonArray.optJSONObject(it)))
            }
            return res
        }

        fun parse(dbIngressUser: DbIngressUser): IngressUser {
            val user = IngressUser()
            user.name = dbIngressUser.name
            user.email = dbIngressUser.email
            user.inserted_date = dbIngressUser.inserted_date
            user.updated_date = dbIngressUser.updated_date
            return user
        }

    }
}

//
data class ImageUrl(
        var url: String? = "",
        var uploader: IngressUser? = IngressUser(),
        var inserted_date: String? = "13971213124532",
        var updated_date: String? = "13971213124532"
) {
    companion object {
        fun parse(jsonImageUrl: JSONObject): ImageUrl {
            val url = ImageUrl()
            url.url = jsonImageUrl.optString("url")
            url.uploader = IngressUser.parse(jsonImageUrl.optJSONObject("uploader"))
            url.inserted_date = jsonImageUrl.optString("inserted_date")
            url.updated_date = jsonImageUrl.optString("updated_date")
            return url
        }

        fun parseAll(imageJsonArray: JSONArray): List<ImageUrl> {
            val res = mutableListOf<ImageUrl>()
            (0 until imageJsonArray.length()).forEach {
                res.add(ImageUrl.parse(imageJsonArray.optJSONObject(it)))
            }
            return res
        }
    }
}

//
data class PortalLocation(
        var id: String? = "",
        var lat: Double? = 0.0,
        var lon: Double? = 0.0,
        var uploader: IngressUser? = IngressUser(),
        var inserted_date: String? = "13971213124532",
        var updated_date: String? = "13971213124532"
) {
    companion object {
        fun parse(jsonObject: JSONObject): PortalLocation {
            val pt = PortalLocation()
            pt.id = jsonObject.optString("id")
            pt.lat = jsonObject.optDouble("lat")
            pt.lon = jsonObject.optDouble("lon")
            pt.uploader = IngressUser.parse(jsonObject.optJSONObject("uploader"))
            pt.inserted_date = jsonObject.optString("inserted_date")
            pt.updated_date = jsonObject.optString("updated_date")
            return pt
        }

        fun parseAll(jsonArray: JSONArray): List<PortalLocation> {
            val res = mutableListOf<PortalLocation>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }
    }
}

data class PortalJuncLocation(
        var id: String? = "",
        var portal: Portal? = Portal(),
        var location: PortalLocation? = PortalLocation(),
        var inserted_date: String? = "",
        var updated_date: String? = ""
) {
    companion object {
        fun parse(jsonObject: JSONObject): PortalJuncLocation {
            val res = PortalJuncLocation()
            res.id = jsonObject.optString("id")
            res.portal = Portal.parse(jsonObject.optJSONObject("portal"))
            res.location = PortalLocation.parse(jsonObject.optJSONObject("location"))
            res.inserted_date = jsonObject.optString("inserted_date")
            res.updated_date = jsonObject.optString("updated_date")
            return res
        }

        fun parseAll(jsonArray: JSONArray): List<PortalJuncLocation> {
            val res = mutableListOf<PortalJuncLocation>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }
    }
}

data class PortalLike(
        var id: String? = "id",
        var portal: Portal? = Portal(),
        var username: IngressUser? = IngressUser(),
        var inserted_date: String? = "",
        var updated_date: String? = ""
) {
    companion object {
        fun parse(jsonObject: JSONObject): PortalLike {
            val res = PortalLike()
            res.id = jsonObject.optString("id")
            res.portal = Portal.parse(jsonObject.optJSONObject("portal"))
            res.username = IngressUser.parse(jsonObject.optJSONObject("user"))
            res.inserted_date = jsonObject.optString("inserted_date")
            res.updated_date = jsonObject.optString("updated_date")
            return res
        }

        fun parseAll(jsonArray: JSONArray): List<PortalLike> {
            val res = mutableListOf<PortalLike>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }

        fun parse(dbIngressUsers: List<DbIngressUser>, dblike: DbPortalLike): PortalLike {
            val res = PortalLike()
            res.id = dblike.id
            res.inserted_date = dblike.inserted_date
            res.updated_date = dblike.updated_date
            res.username = IngressUser.parse(DbIngressUser.getByName(dbIngressUsers, dblike.username!!))
            // TODO: Portal?
            return res
        }

        fun parseAll(dbIngressUser: List<DbIngressUser>, dbLikes: List<DbPortalLike>): List<PortalLike> {
            val res = mutableListOf<PortalLike>()
            dbLikes.forEach {
                res.add(parse(dbIngressUser, it))
            }
            return res
        }
    }
}


data class PortalImage(
        var id: String? = "",
        var portal: Portal? = Portal(),
        var image: ImageUrl? = ImageUrl(),
        var inserted_date: String? = "",
        var updated_date: String? = ""
) {
    companion object {
        fun parse(jsonObject: JSONObject): PortalImage {
            val res = PortalImage()
            res.id = jsonObject.optString("id")
            res.portal = Portal.parse(jsonObject.optJSONObject("portal"))
            res.image = ImageUrl.parse(jsonObject.optJSONObject("image"))
            res.inserted_date = jsonObject.optString("inserted_date")
            res.updated_date = jsonObject.optString("updated_date")
            return res
        }

        fun parseAll(jsonArray: JSONArray): List<PortalImage> {
            val res = mutableListOf<PortalImage>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }
    }
}

data class PortalReport(
        var id: String? = "",
        var portal: Portal? = Portal(),
        var description: String? = "",
        var username: IngressUser? = IngressUser(),
        var inserted_date: String? = "",
        var updated_date: String? = ""
) {
    companion object {
        fun parse(jsonObject: JSONObject): PortalReport {
            val res = PortalReport()
            res.id = jsonObject.optString("id")
            res.portal = Portal.parse(jsonObject.optJSONObject("portal"))
            res.description = jsonObject.optString("description")
            res.username = IngressUser.parse(jsonObject.optJSONObject("username"))
            res.inserted_date = jsonObject.optString("inserted_date")
            res.updated_date = jsonObject.optString("updated_date")

            return res
        }

        fun parseAll(jsonArray: JSONArray): List<PortalReport> {
            val res = mutableListOf<PortalReport>()
            (0 until jsonArray.length()).forEach {
                res.add(parse(jsonArray.optJSONObject(it)))
            }
            return res
        }

        private fun parse(dbIngressUserList: List<DbIngressUser>, it: DbPortalReport): PortalReport {
            val res = PortalReport()
            res.id = it.id
            res.portal = null
            res.description = it.description
            res.username = IngressUser.parse(DbIngressUser.getByName(dbIngressUserList, it.username!!))
            res.inserted_date = it.inserted_date
            res.updated_date = it.updated_date

            return res
        }

        fun parseAll(dbIngressUserList: List<DbIngressUser>, dbReports: List<DbPortalReport>): List<PortalReport> {
            val res = mutableListOf<PortalReport>()
            dbReports.forEach {
                res.add(parse(dbIngressUserList, it))
            }

            return res
        }

    }
}
package ir.mrahimy.ingress.portal.app.model

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
        var likes: List<IngressUser>? = listOf(),
        var reports: List<IngressUser>? = listOf(),
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
            portal.likes = IngressUser.parseAll(jsonPortal.optJSONArray("likes"))
            portal.reports = IngressUser.parseAll(jsonPortal.optJSONArray("reports"))
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
    }
}

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
            url.uploader = IngressUser.parse(jsonImageUrl.optJSONObject("user"))
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
package ir.mrahimy.ingress.portal.sync

import android.net.Uri

/**
 * Created by vincent on 11/26/18.
 */

object PortalContract {
    val CONTENT_AUTHORITY = "ir.mrahimy.ingress.portal"
    val BASE_CONTENT_URI = Uri.parse("content://$CONTENT_AUTHORITY")
    val PATH_PORTALS = "portal"
    val PATH_IMAGE_URLS = "image_url"
    val PATH_Ingress_User = "ingress_user"
    val PATH_portal_junc_location = "portal_junc_location"
    val PATH_portal_like = "portal_like"
    val PATH_portal_image = "portal_image"
    val PATH_portal_location = "portal_location"
    val PATH_portal_report = "portal_report"
    val DB_NAME = "portals_db.db3"
    val DB_VERSION = 5

    object Portal {
        val TABLE_NAME = "portal"
        val COL_id = "id"
        val COL_title = "title"
        val COL_description = "description"
        val COL_uploader = "uploader"
        val COL_inserted_date = "inserted_date"
        val COL_updated_date = "updated_date"

        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PORTALS).build()
        val CONTENT_TYPE = "vnd.android.cursor.dir/$CONTENT_URI/$PATH_PORTALS"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$CONTENT_URI/$PATH_PORTALS"
    }

    object ImageUrl {
        val TABLE_NAME = "image_url"
        val COL_url = "url"
        val COL_uploader = "uploader"
        val COL_inserted_date = "inserted_date"
        val COL_updated_date = "updated_date"


        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_IMAGE_URLS).build()
        val CONTENT_TYPE = "vnd.android.cursor.dir/$CONTENT_URI/$PATH_IMAGE_URLS"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$CONTENT_URI/$PATH_IMAGE_URLS"
    }

    object IngressUser {
        val TABLE_NAME = "ingress_user"
        val COL_name = "name"
        val COL_email = "email"
        val COL_inserted_date = "inserted_date"
        val COL_updated_date = "updated_date"


        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_Ingress_User).build()
        val CONTENT_TYPE = "vnd.android.cursor.dir/$CONTENT_URI/$PATH_Ingress_User"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$CONTENT_URI/$PATH_Ingress_User"

    }

    object PortalJuncLocation {
        val TABLE_NAME = "portal_junc_location"
        val COL_id = "id"
        val COL_portalID = "portal_id"
        val COL_locationID = "location_id"
        val COL_isMain = "is_main"
        val COL_inserted_date = "inserted_date"
        val COL_updated_date = "updated_date"

        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_portal_junc_location).build()
        val CONTENT_TYPE = "vnd.android.cursor.dir/$CONTENT_URI/$PATH_portal_junc_location"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$CONTENT_URI/$PATH_portal_junc_location"
    }

    object PortalLike {
        val TABLE_NAME = "portal_like"
        val COL_id = "id"
        val COL_portalID = "portal_id"
        val COL_username = "username"
        val COL_like = "_like"
        val COL_inserted_date = "inserted_date"
        val COL_updated_date = "updated_date"

        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_portal_like).build()
        val CONTENT_TYPE = "vnd.android.cursor.dir/$CONTENT_URI/$PATH_portal_like"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$CONTENT_URI/$PATH_portal_like"
    }

    object PortalImage {
        val TABLE_NAME = "portal_image"
        val COL_id = "id"
        val COL_portalID = "portal_id"
        val COL_url = "url"
        val COL_inserted_date = "inserted_date"
        val COL_updated_date = "updated_date"

        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_portal_image).build()
        val CONTENT_TYPE = "vnd.android.cursor.dir/$CONTENT_URI/$PATH_portal_image"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$CONTENT_URI/$PATH_portal_image"
    }

    object PortalLocation {
        val TABLE_NAME = "portal_location"
        val COL_id = "id"
        val COL_lat = "lat"
        val COL_lon = "lon"
        val COL_address = "address"
        val COL_uploader_name = "uploader_name"
        val COL_inserted_date = "inserted_date"
        val COL_updated_date = "updated_date"

        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_portal_location).build()
        val CONTENT_TYPE = "vnd.android.cursor.dir/$CONTENT_URI/$PATH_portal_location"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$CONTENT_URI/$PATH_portal_location"
    }

    object PortalReport {
        val TABLE_NAME = "portal_report"
        val COL_id = "id"
        val COL_portal_id = "portal_id"
        val COL_description = "description"
        val COL_username = "username"
        val COL_inserted_date = "inserted_date"
        val COL_updated_date = "updated_date"

        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_portal_report).build()
        val CONTENT_TYPE = "vnd.android.cursor.dir/$CONTENT_URI/$PATH_portal_report"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$CONTENT_URI/$PATH_portal_report"
    }
}

package ir.mrahimy.ingress.portal.sync

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

/**
 * Created by vincent on 11/27/18.
 */

class PortalProvider : ContentProvider() {

    private var db: SQLiteDatabase? = null

    override fun onCreate(): Boolean {
        this.db = DatabaseClient.getInstance(context)!!.db
        return true
    }

    override fun getType(uri: Uri): String? {
        // Find the MIME type of the results... multiple results or a single result
        return when (uriMatcher.match(uri)) {
            /*
              Portal_Report = 801
              Portal_Report_ID = 802*/

            PORTAL -> PortalContract.Portal.CONTENT_TYPE
            PORTAL_ID -> PortalContract.Portal.CONTENT_ITEM_TYPE
            IMAGE_URL -> PortalContract.ImageUrl.CONTENT_TYPE
            IMAGE_URL_ID -> PortalContract.ImageUrl.CONTENT_ITEM_TYPE
            INGRESS_USER -> PortalContract.IngressUser.CONTENT_TYPE
            INGRESS_USER_ID -> PortalContract.IngressUser.CONTENT_ITEM_TYPE
            Portal_Junc_Location -> PortalContract.PortalJuncLocation.CONTENT_TYPE
            Portal_Junc_Location_ID -> PortalContract.PortalJuncLocation.CONTENT_ITEM_TYPE
            Portal_Like -> PortalContract.PortalLike.CONTENT_TYPE
            Portal_Like_ID -> PortalContract.PortalLike.CONTENT_ITEM_TYPE
            Portal_Image -> PortalContract.PortalImage.CONTENT_TYPE
            Portal_Image_ID -> PortalContract.PortalImage.CONTENT_ITEM_TYPE
            Portal_Location -> PortalContract.PortalLocation.CONTENT_TYPE
            Portal_Location_ID -> PortalContract.PortalLocation.CONTENT_ITEM_TYPE
            Portal_Report -> PortalContract.PortalReport.CONTENT_TYPE
            Portal_Report_ID -> PortalContract.PortalReport.CONTENT_ITEM_TYPE
            else -> throw IllegalArgumentException("Invalid URI!")
        }
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val c: Cursor
        when (uriMatcher.match(uri)) {
            // Query for multiple results
            PORTAL -> c = db!!.query(PortalContract.Portal.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs, null, null,
                    sortOrder)

            // Query for single result
            PORTAL_ID -> {
                val _id = ContentUris.parseId(uri)
                c = db!!.query(PortalContract.Portal.TABLE_NAME,
                        projection,
                        PortalContract.Portal.COL_id + "=?",
                        arrayOf(_id.toString()), null, null,
                        sortOrder)
            }
            IMAGE_URL -> c = db!!.query(PortalContract.ImageUrl.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs, null, null,
                    sortOrder)

            // Query for single result
            IMAGE_URL_ID -> {
                val _id = ContentUris.parseId(uri)
                c = db!!.query(PortalContract.ImageUrl.TABLE_NAME,
                        projection,
                        PortalContract.ImageUrl.COL_url + "=?",
                        arrayOf(_id.toString()), null, null,
                        sortOrder)
            }
            INGRESS_USER -> c = db!!.query(PortalContract.IngressUser.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs, null, null,
                    sortOrder)

            // Query for single result
            INGRESS_USER_ID -> {
                val _id = ContentUris.parseId(uri)
                c = db!!.query(PortalContract.IngressUser.TABLE_NAME,
                        projection,
                        PortalContract.IngressUser.COL_name + "=?",
                        arrayOf(_id.toString()), null, null,
                        sortOrder)
            }
            Portal_Junc_Location -> c = db!!.query(PortalContract.PortalJuncLocation.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs, null, null,
                    sortOrder)

            // Query for single result
            Portal_Junc_Location_ID -> {
                val _id = ContentUris.parseId(uri)
                c = db!!.query(PortalContract.PortalJuncLocation.TABLE_NAME,
                        projection,
                        PortalContract.PortalJuncLocation.COL_id + "=?",
                        arrayOf(_id.toString()), null, null,
                        sortOrder)
            }
            Portal_Like -> c = db!!.query(PortalContract.PortalLike.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs, null, null,
                    sortOrder)

            // Query for single result
            Portal_Like_ID -> {
                val _id = ContentUris.parseId(uri)
                c = db!!.query(PortalContract.PortalLike.TABLE_NAME,
                        projection,
                        PortalContract.PortalLike.COL_id + "=?",
                        arrayOf(_id.toString()), null, null,
                        sortOrder)
            }
            Portal_Image -> c = db!!.query(PortalContract.PortalImage.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs, null, null,
                    sortOrder)

            // Query for single result
            Portal_Image_ID -> {
                val _id = ContentUris.parseId(uri)
                c = db!!.query(PortalContract.PortalImage.TABLE_NAME,
                        projection,
                        PortalContract.PortalImage.COL_id + "=?",
                        arrayOf(_id.toString()), null, null,
                        sortOrder)
            }
            Portal_Location -> c = db!!.query(PortalContract.PortalLocation.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs, null, null,
                    sortOrder)

            // Query for single result
            Portal_Location_ID -> {
                val _id = ContentUris.parseId(uri)
                c = db!!.query(PortalContract.PortalLocation.TABLE_NAME,
                        projection,
                        PortalContract.PortalLocation.COL_id + "=?",
                        arrayOf(_id.toString()), null, null,
                        sortOrder)
            }
            Portal_Report -> c = db!!.query(PortalContract.PortalReport.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs, null, null,
                    sortOrder)

            // Query for single result
            Portal_Report_ID -> {
                val _id = ContentUris.parseId(uri)
                c = db!!.query(PortalContract.PortalReport.TABLE_NAME,
                        projection,
                        PortalContract.PortalReport.COL_id + "=?",
                        arrayOf(_id.toString()), null, null,
                        sortOrder)
            }
            else -> throw IllegalArgumentException("Invalid URI!")
        }

        // Tell the cursor to register a content observer to observe changes to the
        // URI or its descendants.
        assert(context != null)
        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val returnUri: Uri
        val _id: Long

        when (uriMatcher.match(uri)) {
            PORTAL -> {
                _id = db!!.insert(PortalContract.Portal.TABLE_NAME, null, values)
                returnUri = ContentUris.withAppendedId(PortalContract.Portal.CONTENT_URI, _id)
            }
            IMAGE_URL -> {
                _id = db!!.insert(PortalContract.ImageUrl.TABLE_NAME, null, values)
                returnUri = ContentUris.withAppendedId(PortalContract.Portal.CONTENT_URI, _id)
            }
            INGRESS_USER -> {
                _id = db!!.insert(PortalContract.IngressUser.TABLE_NAME, null, values)
                returnUri = ContentUris.withAppendedId(PortalContract.Portal.CONTENT_URI, _id)
            }
            Portal_Junc_Location -> {
                _id = db!!.insert(PortalContract.PortalJuncLocation.TABLE_NAME, null, values)
                returnUri = ContentUris.withAppendedId(PortalContract.Portal.CONTENT_URI, _id)
            }
            Portal_Like -> {
                _id = db!!.insert(PortalContract.PortalLike.TABLE_NAME, null, values)
                returnUri = ContentUris.withAppendedId(PortalContract.Portal.CONTENT_URI, _id)
            }
            Portal_Image -> {
                _id = db!!.insert(PortalContract.PortalImage.TABLE_NAME, null, values)
                returnUri = ContentUris.withAppendedId(PortalContract.Portal.CONTENT_URI, _id)
            }
            Portal_Location -> {
                _id = db!!.insert(PortalContract.PortalLocation.TABLE_NAME, null, values)
                returnUri = ContentUris.withAppendedId(PortalContract.Portal.CONTENT_URI, _id)
            }
            Portal_Report -> {
                _id = db!!.insert(PortalContract.PortalReport.TABLE_NAME, null, values)
                returnUri = ContentUris.withAppendedId(PortalContract.Portal.CONTENT_URI, _id)
            }
            else -> throw IllegalArgumentException("Invalid URI!")
        }

        // Notify any observers to update the UI
        assert(context != null)
        context!!.contentResolver.notifyChange(uri, null)
        return returnUri
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val rows: Int
        //TODO:
        when (uriMatcher.match(uri)) {
            PORTAL -> rows = db!!.update(PortalContract.Portal.TABLE_NAME, values, selection, selectionArgs)
            IMAGE_URL -> rows = db!!.update(PortalContract.ImageUrl.TABLE_NAME, values, selection, selectionArgs)
            INGRESS_USER -> rows = db!!.update(PortalContract.IngressUser.TABLE_NAME, values, selection, selectionArgs)
            Portal_Junc_Location -> rows = db!!.update(PortalContract.PortalJuncLocation.TABLE_NAME, values, selection, selectionArgs)
            Portal_Like -> rows = db!!.update(PortalContract.PortalLike.TABLE_NAME, values, selection, selectionArgs)
            Portal_Image -> rows = db!!.update(PortalContract.PortalImage.TABLE_NAME, values, selection, selectionArgs)
            Portal_Location -> rows = db!!.update(PortalContract.PortalLocation.TABLE_NAME, values, selection, selectionArgs)
            Portal_Report -> rows = db!!.update(PortalContract.PortalReport.TABLE_NAME, values, selection, selectionArgs)
            else -> throw IllegalArgumentException("Invalid URI!")
        }

        // Notify any observers to update the UI
        if (rows != 0) {
            assert(context != null)
            context!!.contentResolver.notifyChange(uri, null)
        }
        return rows
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val rows: Int
        when (uriMatcher.match(uri)) {
            PORTAL -> rows = db!!.delete(PortalContract.Portal.TABLE_NAME, selection, selectionArgs)
            IMAGE_URL -> rows = db!!.delete(PortalContract.ImageUrl.TABLE_NAME, selection, selectionArgs)
            INGRESS_USER -> rows = db!!.delete(PortalContract.IngressUser.TABLE_NAME, selection, selectionArgs)
            Portal_Junc_Location -> rows = db!!.delete(PortalContract.PortalJuncLocation.TABLE_NAME, selection, selectionArgs)
            Portal_Like -> rows = db!!.delete(PortalContract.PortalLike.TABLE_NAME, selection, selectionArgs)
            Portal_Image -> rows = db!!.delete(PortalContract.PortalImage.TABLE_NAME, selection, selectionArgs)
            Portal_Location -> rows = db!!.delete(PortalContract.PortalLocation.TABLE_NAME, selection, selectionArgs)
            Portal_Report -> rows = db!!.delete(PortalContract.PortalReport.TABLE_NAME, selection, selectionArgs)
            else -> throw IllegalArgumentException("Invalid URI!")
        }

        // Notify any observers to update the UI
        if (rows != 0) {
            assert(context != null)
            context!!.contentResolver.notifyChange(uri, null)
        }
        return rows
    }

    companion object {
        // Use ints to represent different queries
        private const val PORTAL = 101
        private const val PORTAL_ID = 102
        private const val IMAGE_URL = 201
        private const val IMAGE_URL_ID = 202
        private const val INGRESS_USER = 301
        private const val INGRESS_USER_ID = 302
        private const val Portal_Junc_Location = 401
        private const val Portal_Junc_Location_ID = 402
        private const val Portal_Like = 501
        private const val Portal_Like_ID = 502
        private const val Portal_Image = 601
        private const val Portal_Image_ID = 602

        private const val Portal_Location = 701
        private const val Portal_Location_ID = 702

        private const val Portal_Report = 801
        private const val Portal_Report_ID = 802

        private val uriMatcher: UriMatcher

        init {
            // Add all our query types to our UriMatcher
            //TODO:
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_PORTALS, PORTAL)
            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_PORTALS + "/#", PORTAL_ID)

            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_IMAGE_URLS, IMAGE_URL)
            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_IMAGE_URLS + "/#", IMAGE_URL_ID)

            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_Ingress_User, INGRESS_USER)
            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_Ingress_User + "/#", INGRESS_USER_ID)

            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_portal_junc_location, Portal_Junc_Location)
            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_portal_junc_location + "/#", Portal_Junc_Location_ID)

            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_portal_like, Portal_Like)
            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_portal_like + "/#", Portal_Like_ID)

            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_portal_image, Portal_Image)
            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_portal_image + "/#", Portal_Image_ID)

            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_portal_location, Portal_Location)
            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_portal_location + "/#", Portal_Location_ID)

            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_portal_report, Portal_Report)
            uriMatcher.addURI(PortalContract.CONTENT_AUTHORITY, PortalContract.PATH_portal_report + "/#", Portal_Report_ID)

        }
    }
}

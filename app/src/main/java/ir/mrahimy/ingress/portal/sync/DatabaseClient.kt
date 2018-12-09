package ir.mrahimy.ingress.portal.sync

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.mrahimy.ingress.portal.sync.PortalContract.DB_NAME
import ir.mrahimy.ingress.portal.sync.PortalContract.DB_VERSION

/**
 * Created by vincent on 11/26/18.
 */

class DatabaseClient private constructor(c: Context) : SQLiteOpenHelper(c, DB_NAME, null, DB_VERSION) {
    /**
     * Provide access to our database.
     */
    val db: SQLiteDatabase

    init {
        this.db = writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create any SQLite tables here
        createPortalTable(db)
        createImageUrlTable(db)
        createIngressUserTable(db)
        createPortalJuntLocationTable(db)
        createPortalLikeTable(db)
        createPortalImageTable(db)
        createPortalLocationTable(db)
        createPortalReportTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Update any SQLite tables here
        db.execSQL("DROP TABLE IF EXISTS [" + PortalContract.Portal.TABLE_NAME + "];")
        db.execSQL("DROP TABLE IF EXISTS [" + PortalContract.ImageUrl.TABLE_NAME + "];")
        db.execSQL("DROP TABLE IF EXISTS [" + PortalContract.IngressUser.TABLE_NAME + "];")
        db.execSQL("DROP TABLE IF EXISTS [" + PortalContract.PortalJuncLocation.TABLE_NAME + "];")
        db.execSQL("DROP TABLE IF EXISTS [" + PortalContract.PortalLike.TABLE_NAME + "];")
        db.execSQL("DROP TABLE IF EXISTS [" + PortalContract.PortalImage.TABLE_NAME + "];")
        db.execSQL("DROP TABLE IF EXISTS [" + PortalContract.PortalLocation.TABLE_NAME + "];")
        db.execSQL("DROP TABLE IF EXISTS [" + PortalContract.PortalReport.TABLE_NAME + "];")
        onCreate(db)
    }

    /**
     * Creates our 'portal' SQLite database table.
     * @param db [SQLiteDatabase]
     */
    private fun createPortalTable(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE [" + PortalContract.Portal.TABLE_NAME + "] ([" +
                PortalContract.Portal.COL_id + "] TEXT UNIQUE PRIMARY KEY,[" +
                PortalContract.Portal.COL_title + "] TEXT,[" +
                PortalContract.Portal.COL_description + "] TEXT,[" +
                PortalContract.Portal.COL_uploader + "] TEXT NOT NULL,[" +
                PortalContract.Portal.COL_inserted_date + "] TEXT,[" +
                PortalContract.Portal.COL_updated_date + "] TEXT);")
    }

    /**
     * Creates our 'imageurl' SQLite database table.
     * @param db [SQLiteDatabase]
     */
    private fun createImageUrlTable(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE [" + PortalContract.ImageUrl.TABLE_NAME + "] ([" +
                PortalContract.ImageUrl.COL_url + "] TEXT UNIQUE PRIMARY KEY,[" +
                PortalContract.ImageUrl.COL_uploader + "] TEXT NOT NULL,[" +
                PortalContract.ImageUrl.COL_inserted_date + "] TEXT,[" +
                PortalContract.ImageUrl.COL_updated_date + "] TEXT);")
    }


    /**
     * Creates our 'portal' SQLite database table.
     * @param db [SQLiteDatabase]
     */
    private fun createIngressUserTable(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE [" + PortalContract.IngressUser.TABLE_NAME + "] ([" +
                PortalContract.IngressUser.COL_name + "] TEXT UNIQUE PRIMARY KEY,[" +
                PortalContract.IngressUser.COL_email + "] TEXT,[" +
                PortalContract.IngressUser.COL_inserted_date + "] TEXT,[" +
                PortalContract.IngressUser.COL_updated_date + "] TEXT);")
    }

    /**
     * Creates our 'portal' SQLite database table.
     * @param db [SQLiteDatabase]
     */
    private fun createPortalJuntLocationTable(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE [" + PortalContract.PortalJuncLocation.TABLE_NAME + "] ([" +
                PortalContract.PortalJuncLocation.COL_id + "] TEXT UNIQUE PRIMARY KEY,[" +
                PortalContract.PortalJuncLocation.COL_portalID + "] TEXT,[" +
                PortalContract.PortalJuncLocation.COL_locationID + "] TEXT,[" +
                PortalContract.PortalJuncLocation.COL_inserted_date + "] TEXT,[" +
                PortalContract.PortalJuncLocation.COL_updated_date + "] TEXT);")
    }
    /**
     * Creates our 'portal' SQLite database table.
     * @param db [SQLiteDatabase]
     */
    private fun createPortalLikeTable(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE [" + PortalContract.PortalLike.TABLE_NAME + "] ([" +
                PortalContract.PortalLike.COL_id + "] TEXT UNIQUE PRIMARY KEY,[" +
                PortalContract.PortalLike.COL_portalID + "] TEXT,[" +
                PortalContract.PortalLike.COL_username + "] TEXT,[" +
                PortalContract.PortalLike.COL_like + "] NUMERIC,[" +
                PortalContract.PortalLike.COL_inserted_date + "] TEXT,[" +
                PortalContract.PortalLike.COL_updated_date + "] TEXT);")
    }
/**
     * Creates our 'portal' SQLite database table.
     * @param db [SQLiteDatabase]
     */
    private fun createPortalImageTable(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE [" + PortalContract.PortalImage.TABLE_NAME + "] ([" +
                PortalContract.PortalImage.COL_id + "] TEXT UNIQUE PRIMARY KEY,[" +
                PortalContract.PortalImage.COL_portalID + "] TEXT,[" +
                PortalContract.PortalImage.COL_url + "] TEXT,[" +
                PortalContract.PortalImage.COL_inserted_date + "] TEXT,[" +
                PortalContract.PortalImage.COL_updated_date + "] TEXT);")
    }
/**
     * Creates our 'portal' SQLite database table.
     * @param db [SQLiteDatabase]
     */
    private fun createPortalLocationTable(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE [" + PortalContract.PortalLocation.TABLE_NAME + "] ([" +
                PortalContract.PortalLocation.COL_id + "] TEXT UNIQUE PRIMARY KEY,[" +
                PortalContract.PortalLocation.COL_lat + "] REAL,[" +
                PortalContract.PortalLocation.COL_lon + "] REAL,[" +
                PortalContract.PortalLocation.COL_uploader_name + "] TEXT,[" +
                PortalContract.PortalLocation.COL_inserted_date + "] TEXT,[" +
                PortalContract.PortalLocation.COL_updated_date + "] TEXT);")
    }
/**
     * Creates our 'portal' SQLite database table.
     * @param db [SQLiteDatabase]
     */
    private fun createPortalReportTable(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE [" + PortalContract.PortalReport.TABLE_NAME + "] ([" +
                PortalContract.PortalReport.COL_id + "] TEXT UNIQUE PRIMARY KEY,[" +
                PortalContract.PortalReport.COL_portal_id + "] TEXT,[" +
                PortalContract.PortalReport.COL_description + "] TEXT,[" +
                PortalContract.PortalReport.COL_username + "] TEXT,[" +
                PortalContract.PortalReport.COL_inserted_date + "] TEXT,[" +
                PortalContract.PortalReport.COL_updated_date + "] TEXT);")
    }

    companion object {
        @Volatile
        private var instance: DatabaseClient? = null

        /**
         * We use a Singleton to prevent leaking the SQLiteDatabase or Context.
         * @return [DatabaseClient]
         */
        fun getInstance(c: Context): DatabaseClient? {
            if (instance == null) {
                synchronized(DatabaseClient::class.java) {
                    if (instance == null) {
                        instance = DatabaseClient(c)
                    }
                }
            }
            return instance
        }
    }
}
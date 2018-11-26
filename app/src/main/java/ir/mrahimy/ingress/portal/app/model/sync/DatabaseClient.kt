package ir.mrahimy.ingress.portal.app.model.sync

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ir.mrahimy.ingress.portal.app.model.sync.PortalContract.DB_NAME
import ir.mrahimy.ingress.portal.app.model.sync.PortalContract.DB_VERSION

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
     * Creates our 'articles' SQLite database table.
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
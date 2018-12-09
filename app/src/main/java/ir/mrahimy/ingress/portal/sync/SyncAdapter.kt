package ir.mrahimy.ingress.portal.sync

import android.accounts.Account
import android.content.*
import android.os.Bundle
import android.os.RemoteException
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import ir.mrahimy.ingress.portal.net.PortalRestClient
import timber.log.Timber
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import android.content.Intent
import android.content.ContentProviderOperation
import android.util.Log
import ir.mrahimy.ingress.portal.dbmodel.*
import org.json.JSONObject


/**
 * This is used by the Android framework to perform synchronization. IMPORTANT: do NOT create
 * new Threads to perform logic, Android will do this for you; hence, the name.
 *
 *
 * The goal here to perform synchronization, is to do it efficiently as possible. We use some
 * ContentProvider features to batch our writes to the local data source. Be sure to handle all
 * possible exceptions accordingly; random crashes is not a good user-experience.
 */
class SyncAdapter @JvmOverloads constructor(context: Context, autoInitialize: Boolean, allowParallelSyncs: Boolean = false) : AbstractThreadedSyncAdapter(context, autoInitialize, allowParallelSyncs) {
    private val contentResolver: ContentResolver = context.contentResolver
    //private
    override fun onPerformSync(account: Account, bundle: Bundle, s: String, contentProviderClient: ContentProviderClient, syncResult: SyncResult) {
        sync(syncResult)

        val i = Intent(SYNC_FINISHED)
        context.sendBroadcast(i)
    }

    private fun sync(syncResult: SyncResult) {
        Log.d(TAG, "sync started")
        /*try {*/
            syncPortals(syncResult)
        /*} catch (ex: IOException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numParseExceptions++
        } catch (ex: RemoteException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        } catch (ex: OperationApplicationException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        }*/

        try {
            syncImageUrls(syncResult)
        } catch (ex: IOException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numParseExceptions++
        } catch (ex: RemoteException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        } catch (ex: OperationApplicationException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncIngressUser(syncResult)
        } catch (ex: IOException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numParseExceptions++
        } catch (ex: RemoteException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        } catch (ex: OperationApplicationException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncPortalJuncLoc(syncResult)
        } catch (ex: IOException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numParseExceptions++
        } catch (ex: RemoteException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        } catch (ex: OperationApplicationException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncLikes(syncResult)
        } catch (ex: IOException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numParseExceptions++
        } catch (ex: RemoteException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        } catch (ex: OperationApplicationException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncImages(syncResult)
        } catch (ex: IOException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numParseExceptions++
        } catch (ex: RemoteException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        } catch (ex: OperationApplicationException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncPortalLocation(syncResult)
        } catch (ex: IOException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numParseExceptions++
        } catch (ex: RemoteException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        } catch (ex: OperationApplicationException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncPortalReport(syncResult)
        } catch (ex: IOException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numParseExceptions++
        } catch (ex: RemoteException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        } catch (ex: OperationApplicationException) {
            Timber.e("$TAG, Error synchronizing! $ex")
            syncResult.stats.numAuthExceptions++
        }
    }

    private fun syncPortalReport(syncResult: SyncResult) {
        val nets = mutableMapOf<String, DbPortalReport>()
        PortalRestClient.getSync(PortalContract.PATH_portal_report, RequestParams(), object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("syncACTIONS: $response")
                for (i in 0 until response!!.length()) {
                    val element = DbPortalReport.parse(response.optJSONObject(i))
                    nets[element.id!!] = element
                }
                val batch = arrayListOf<ContentProviderOperation>()
                val c = contentResolver.query(PortalContract.PortalReport.CONTENT_URI, null, null, null, null)
                c?.moveToFirst()

                var id = ""
                var pid = ""
                var description = ""
                var username = ""
                var inserted_date = "13971213124532"
                var updated_date = "13971213124532"

                var netPoLoc: DbPortalReport?

                (0 until c!!.count).forEach {
                    syncResult.stats.numEntries++

                    id = c.getString(c.getColumnIndex(PortalContract.PortalReport.COL_id))
                    pid = c.getString(c.getColumnIndex(PortalContract.PortalReport.COL_portal_id))
                    description = c.getString(c.getColumnIndex(PortalContract.PortalReport.COL_description))
                    username = c.getString(c.getColumnIndex(PortalContract.PortalReport.COL_username))
                    inserted_date = c.getString(c.getColumnIndex(PortalContract.PortalReport.COL_inserted_date))
                    updated_date = c.getString(c.getColumnIndex(PortalContract.PortalReport.COL_updated_date))

                    netPoLoc = nets[id]
                    if (netPoLoc == null) {
                        //exist in local but not server
                        //send to server
                        val local = DbPortalReport(id, pid, description, username,
                                inserted_date, updated_date)
                        sendToServer(local)
                    } else {
                        // does not exist in local
                        nets.remove(id)
                        // update?
                        if (netPoLoc!!.updated_date?.compareTo(updated_date)!! > 0) {
                            //update local
                            batch.add(ContentProviderOperation.newUpdate(PortalContract.PortalReport.CONTENT_URI)
                                    .withSelection(PortalContract.PortalReport.COL_id + "='" + id + "'", null)
                                    .withValue(PortalContract.PortalReport.COL_portal_id, netPoLoc!!.portal_id)
                                    .withValue(PortalContract.PortalReport.COL_description, netPoLoc!!.description)
                                    .withValue(PortalContract.PortalReport.COL_username, netPoLoc!!.username)
                                    .withValue(PortalContract.PortalReport.COL_inserted_date, netPoLoc!!.inserted_date)
                                    .withValue(PortalContract.PortalReport.COL_updated_date, netPoLoc!!.updated_date)
                                    .build())
                            syncResult.stats.numUpdates++
                        } else if (netPoLoc!!.updated_date?.compareTo(updated_date)!! < 0) {
                            // local is newer
                            // send to server
                            val local = DbPortalReport(id, pid, description, username,
                                    inserted_date, updated_date)
                            sendToServer(local)
                        }
                    }//null or not null check done

                    c.moveToNext()
                }//after cursor loop
                c.close()

                for (e in nets.values) {
                    Timber.i("$TAG, Scheduling insert ${e.javaClass.simpleName}: $e")
                    batch.add(ContentProviderOperation.newInsert(PortalContract.PortalReport.CONTENT_URI)
                            .withValue(PortalContract.PortalReport.COL_id, e.id)
                            .withValue(PortalContract.PortalReport.COL_portal_id, e.portal_id)
                            .withValue(PortalContract.PortalReport.COL_description, e.description)
                            .withValue(PortalContract.PortalReport.COL_username, e.username)
                            .withValue(PortalContract.PortalReport.COL_inserted_date, e.inserted_date)
                            .withValue(PortalContract.PortalReport.COL_updated_date, e.updated_date)
                            .build())

                    syncResult.stats.numInserts++
                }

                contentResolver.applyBatch(PortalContract.CONTENT_AUTHORITY, batch)
                contentResolver.notifyChange(PortalContract.PortalReport.CONTENT_URI,
                        null, false)
            }// end onSuccess

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun syncPortalLocation(syncResult: SyncResult) {
        val nets = mutableMapOf<String, DbPortalLocation>()
        PortalRestClient.getSync(PortalContract.PATH_portal_location, RequestParams(), object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("syncACTIONS: $response")
                for (i in 0 until response!!.length()) {
                    val element = DbPortalLocation.parse(response.optJSONObject(i))
                    nets[element.id!!] = element
                }
                val batch = arrayListOf<ContentProviderOperation>()
                val c = contentResolver.query(PortalContract.PortalLocation.CONTENT_URI, null, null, null, null)
                c?.moveToFirst()

                var id = ""
                var lat = 0.0
                var lon = 0.0
                var uploader_name = ""
                var inserted_date = "13971213124532"
                var updated_date = "13971213124532"

                var netPoLoc: DbPortalLocation?

                (0 until c!!.count).forEach {
                    syncResult.stats.numEntries++
                    // Create local entry
                    id = c.getString(c.getColumnIndex(PortalContract.PortalLocation.COL_id))
                    lat = c.getDouble(c.getColumnIndex(PortalContract.PortalLocation.COL_lat))
                    lon = c.getDouble(c.getColumnIndex(PortalContract.PortalLocation.COL_lon))
                    uploader_name = c.getString(c.getColumnIndex(PortalContract.PortalLocation.COL_uploader_name))
                    inserted_date = c.getString(c.getColumnIndex(PortalContract.PortalLocation.COL_inserted_date))
                    updated_date = c.getString(c.getColumnIndex(PortalContract.PortalLocation.COL_updated_date))

                    netPoLoc = nets[id]
                    if (netPoLoc == null) {
                        //exist in local / not server
                        //send to server
                        val local = DbPortalLocation(id, lat, lon, uploader_name,
                                inserted_date, updated_date)
                        sendToServer(local)
                    } else {
                        // does not exist in local
                        nets.remove(id)
                        // update?
                        if (netPoLoc!!.updated_date?.compareTo(updated_date)!! > 0) {
                            //update local
                            batch.add(ContentProviderOperation.newUpdate(PortalContract.PortalLocation.CONTENT_URI)
                                    .withSelection(PortalContract.PortalLocation.COL_id + "='" + id + "'", null)
                                    .withValue(PortalContract.PortalLocation.COL_lat, netPoLoc!!.lat)
                                    .withValue(PortalContract.PortalLocation.COL_lon, netPoLoc!!.lon)
                                    .withValue(PortalContract.PortalLocation.COL_uploader_name, netPoLoc!!.uploader)
                                    .withValue(PortalContract.PortalLocation.COL_inserted_date, netPoLoc!!.inserted_date)
                                    .withValue(PortalContract.PortalLocation.COL_updated_date, netPoLoc!!.updated_date)
                                    .build())
                            syncResult.stats.numUpdates++
                        } else if (netPoLoc!!.updated_date?.compareTo(updated_date)!! < 0) {
                            // local is newer
                            // send to server
                            val local = DbPortalLocation(id, lat, lon, uploader_name,
                                    inserted_date, updated_date)
                            sendToServer(local)
                        }
                    }//null or not null check done

                    c.moveToNext()
                }
                c.close()

                for (e in nets.values) {
                    Timber.i("$TAG, Scheduling insert ${e.javaClass.simpleName}: $e")
                    batch.add(ContentProviderOperation.newInsert(PortalContract.PortalLocation.CONTENT_URI)
                            .withValue(PortalContract.PortalLocation.COL_id, e.id)
                            .withValue(PortalContract.PortalLocation.COL_lat, e.lat)
                            .withValue(PortalContract.PortalLocation.COL_lon, e.lon)
                            .withValue(PortalContract.PortalLocation.COL_uploader_name, e.uploader)
                            .withValue(PortalContract.PortalLocation.COL_inserted_date, e.inserted_date)
                            .withValue(PortalContract.PortalLocation.COL_updated_date, e.updated_date)
                            .build())

                    syncResult.stats.numInserts++
                }

                contentResolver.applyBatch(PortalContract.CONTENT_AUTHORITY, batch)
                contentResolver.notifyChange(PortalContract.PortalLocation.CONTENT_URI,
                        null, false)
            }// end onSuccess

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun syncImages(syncResult: SyncResult) {
        val nets = mutableMapOf<String, DbPortalImage>()
        PortalRestClient.getSync(PortalContract.PATH_portal_image, RequestParams(), object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("syncACTIONS: $response")
                for (i in 0 until response!!.length()) {
                    val element = DbPortalImage.parse(response.optJSONObject(i))
                    nets[element.id!!] = element
                }
                val batch = arrayListOf<ContentProviderOperation>()
                val c = contentResolver.query(PortalContract.PortalImage.CONTENT_URI, null, null, null, null)
                c?.moveToFirst()

                var id = ""
                var pid = ""
                var url = ""
                var inserted_date = "13971213124532"
                var updated_date = "13971213124532"

                var netPortalImage: DbPortalImage?

                (0 until c!!.count).forEach {
                    syncResult.stats.numEntries++
                    // Create local entry
                    id = c.getString(c.getColumnIndex(PortalContract.PortalImage.COL_id))
                    pid = c.getString(c.getColumnIndex(PortalContract.PortalImage.COL_portalID))
                    url = c.getString(c.getColumnIndex(PortalContract.PortalImage.COL_url))
                    inserted_date = c.getString(c.getColumnIndex(PortalContract.PortalImage.COL_inserted_date))
                    updated_date = c.getString(c.getColumnIndex(PortalContract.PortalImage.COL_updated_date))

                    netPortalImage = nets[id]
                    if (netPortalImage == null) {
                        //exist in local / not server
                        //send to server
                        val local = DbPortalImage(id, pid, url,
                                inserted_date, updated_date)
                        sendToServer(local)
                    } else {
                        // does not exist in local
                        nets.remove(id)
                        // update?
                        if (netPortalImage!!.updated_date?.compareTo(updated_date)!! > 0) {
                            //update local
                            batch.add(ContentProviderOperation.newUpdate(PortalContract.PortalImage.CONTENT_URI)
                                    .withSelection(PortalContract.PortalImage.COL_id + "='" + id + "'", null)
                                    .withValue(PortalContract.PortalImage.COL_portalID, netPortalImage!!.portal_id)
                                    .withValue(PortalContract.PortalImage.COL_url, netPortalImage!!.url)
                                    .withValue(PortalContract.PortalImage.COL_inserted_date, netPortalImage!!.inserted_date)
                                    .withValue(PortalContract.PortalImage.COL_updated_date, netPortalImage!!.updated_date)
                                    .build())
                            syncResult.stats.numUpdates++
                        } else if (netPortalImage!!.updated_date?.compareTo(updated_date)!! < 0) {
                            // send to server
                            val local = DbPortalImage(id, pid, url,
                                    inserted_date, updated_date)
                            sendToServer(local)
                        }
                    }//null or not null check done

                    c.moveToNext()
                }
                c.close()

                for (e in nets.values) {
                    Timber.i("$TAG, Scheduling insert ${e.javaClass.simpleName}: $e")
                    batch.add(ContentProviderOperation.newInsert(PortalContract.PortalImage.CONTENT_URI)
                            .withValue(PortalContract.PortalImage.COL_id, e.id)
                            .withValue(PortalContract.PortalImage.COL_portalID, e.portal_id)
                            .withValue(PortalContract.PortalImage.COL_url, e.url)
                            .withValue(PortalContract.PortalImage.COL_inserted_date, e.inserted_date)
                            .withValue(PortalContract.PortalImage.COL_updated_date, e.updated_date)
                            .build())

                    syncResult.stats.numInserts++
                }

                contentResolver.applyBatch(PortalContract.CONTENT_AUTHORITY, batch)
                contentResolver.notifyChange(PortalContract.PortalImage.CONTENT_URI,
                        null, false)
            }// end onSuccess

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun syncLikes(syncResult: SyncResult) {
        val nets = mutableMapOf<String, DbPortalLike>()
        PortalRestClient.getSync(PortalContract.PATH_portal_like, RequestParams(), object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("syncACTIONS: $response")
                for (i in 0 until response!!.length()) {
                    val element = DbPortalLike.parse(response.optJSONObject(i))
                    nets[element.id!!] = element
                }
                val batch = arrayListOf<ContentProviderOperation>()
                val c = contentResolver.query(PortalContract.PortalLike.CONTENT_URI, null, null, null, null)
                c?.moveToFirst()

                var id = ""
                var pid = ""
                var username = ""
                var inserted_date = "13971213124532"
                var updated_date = "13971213124532"

                var netWorkPortalLike: DbPortalLike?

                (0 until c!!.count).forEach {
                    syncResult.stats.numEntries++
                    // Create local entry
                    id = c.getString(c.getColumnIndex(PortalContract.PortalLike.COL_id))
                    pid = c.getString(c.getColumnIndex(PortalContract.PortalLike.COL_portalID))
                    username = c.getString(c.getColumnIndex(PortalContract.PortalLike.COL_username))
                    inserted_date = c.getString(c.getColumnIndex(PortalContract.PortalLike.COL_inserted_date))
                    updated_date = c.getString(c.getColumnIndex(PortalContract.PortalLike.COL_updated_date))

                    netWorkPortalLike = nets[id]
                    if (netWorkPortalLike == null) {
                        //exist in local / not server
                        //send to server
                        val local = DbPortalLike(id, pid, username,
                                inserted_date, updated_date)
                        sendToServer(local)
                    } else {
                        // does not exist in local
                        nets.remove(id)
                        // update?
                        if (netWorkPortalLike!!.updated_date?.compareTo(updated_date)!! > 0) {
                            //update local
                            batch.add(ContentProviderOperation.newUpdate(PortalContract.PortalLike.CONTENT_URI)
                                    .withSelection(PortalContract.PortalLike.COL_id + "='" + id + "'", null)
                                    .withValue(PortalContract.PortalLike.COL_portalID, netWorkPortalLike!!.portal_id)
                                    .withValue(PortalContract.PortalLike.COL_username, netWorkPortalLike!!.username)
                                    .withValue(PortalContract.PortalLike.COL_inserted_date, netWorkPortalLike!!.inserted_date)
                                    .withValue(PortalContract.PortalLike.COL_updated_date, netWorkPortalLike!!.updated_date)
                                    .build())
                            syncResult.stats.numUpdates++
                        } else if (netWorkPortalLike!!.updated_date?.compareTo(updated_date)!! < 0) {
                            // send to server
                            val local = DbPortalLike(id, pid, username,
                                    inserted_date, updated_date)
                            sendToServer(local)
                        }
                    }//null or not null check done

                    c.moveToNext()
                }
                c.close()

                for (e in nets.values) {
                    Timber.i("$TAG, Scheduling insert ${e.javaClass.simpleName}: $e")
                    batch.add(ContentProviderOperation.newInsert(PortalContract.PortalLike.CONTENT_URI)
                            .withValue(PortalContract.PortalLike.COL_id, e.id)
                            .withValue(PortalContract.PortalLike.COL_portalID, e.portal_id)
                            .withValue(PortalContract.PortalLike.COL_username, e.username)
                            .withValue(PortalContract.PortalLike.COL_inserted_date, e.inserted_date)
                            .withValue(PortalContract.PortalLike.COL_updated_date, e.updated_date)
                            .build())

                    syncResult.stats.numInserts++
                }

                contentResolver.applyBatch(PortalContract.CONTENT_AUTHORITY, batch)
                contentResolver.notifyChange(PortalContract.PortalLike.CONTENT_URI,
                        null, false)
            }// end onSuccess

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun syncPortalJuncLoc(syncResult: SyncResult) {
        val nets = mutableMapOf<String, DbPortalJuncLocation>()
        PortalRestClient.getSync(PortalContract.PATH_portal_junc_location, RequestParams(), object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("syncACTIONS: $response")
                for (i in 0 until response!!.length()) {
                    val juncLocation = DbPortalJuncLocation.parse(response.optJSONObject(i))
                    nets[juncLocation.id!!] = juncLocation
                }
                val batch = arrayListOf<ContentProviderOperation>()
                val c = contentResolver.query(PortalContract.PortalJuncLocation.CONTENT_URI, null, null, null, null)
                c?.moveToFirst()

                var id = ""
                var pid = ""
                var lid = ""
                var inserted_date = "13971213124532"
                var updated_date = "13971213124532"

                var networkPortalJuncLocation: DbPortalJuncLocation?

                (0 until c!!.count).forEach {
                    syncResult.stats.numEntries++
                    // Create local  entry
                    id = c.getString(c.getColumnIndex(PortalContract.PortalJuncLocation.COL_id))
                    pid = c.getString(c.getColumnIndex(PortalContract.PortalJuncLocation.COL_portalID))
                    lid = c.getString(c.getColumnIndex(PortalContract.PortalJuncLocation.COL_locationID))
                    inserted_date = c.getString(c.getColumnIndex(PortalContract.PortalJuncLocation.COL_inserted_date))
                    updated_date = c.getString(c.getColumnIndex(PortalContract.PortalJuncLocation.COL_updated_date))

                    networkPortalJuncLocation = nets[id]
                    if (networkPortalJuncLocation == null) {
                        //exist in local / not server
                        //send to server
                        val local = DbPortalJuncLocation(id, pid, lid,
                                inserted_date, updated_date)
                        sendToServer(local)
                    } else {
                        // does not exist in local
                        nets.remove(id)
                        // update?
                        if (networkPortalJuncLocation!!.updated_date?.compareTo(updated_date)!! > 0) {
                            //update local
                            batch.add(ContentProviderOperation.newUpdate(PortalContract.PortalJuncLocation.CONTENT_URI)
                                    .withSelection(PortalContract.PortalJuncLocation.COL_id + "='" + id + "'", null)
                                    .withValue(PortalContract.PortalJuncLocation.COL_portalID, networkPortalJuncLocation!!.portal_id)
                                    .withValue(PortalContract.PortalJuncLocation.COL_locationID, networkPortalJuncLocation!!.location_id)
                                    .withValue(PortalContract.PortalJuncLocation.COL_inserted_date, networkPortalJuncLocation!!.inserted_date)
                                    .withValue(PortalContract.PortalJuncLocation.COL_updated_date, networkPortalJuncLocation!!.updated_date)
                                    .build())
                            syncResult.stats.numUpdates++
                        } else if (networkPortalJuncLocation!!.updated_date?.compareTo(updated_date)!! < 0) {
                            // send to server
                            val local = DbPortalJuncLocation(id, pid, lid,
                                    inserted_date, updated_date)
                            sendToServer(local)
                        }
                    }//null or not null check done

                    c.moveToNext()
                }
                c.close()

                for (e in nets.values) {
                    Timber.i("$TAG, Scheduling insert ${e.javaClass.simpleName}: $e")
                    batch.add(ContentProviderOperation.newInsert(PortalContract.PortalJuncLocation.CONTENT_URI)
                            .withValue(PortalContract.PortalJuncLocation.COL_id, e.id)
                            .withValue(PortalContract.PortalJuncLocation.COL_portalID, e.portal_id)
                            .withValue(PortalContract.PortalJuncLocation.COL_locationID, e.location_id)
                            .withValue(PortalContract.PortalJuncLocation.COL_inserted_date, e.inserted_date)
                            .withValue(PortalContract.PortalJuncLocation.COL_updated_date, e.updated_date)
                            .build())

                    syncResult.stats.numInserts++
                }

                contentResolver.applyBatch(PortalContract.CONTENT_AUTHORITY, batch)
                contentResolver.notifyChange(PortalContract.PortalJuncLocation.CONTENT_URI,
                        null, false)
            }// end onSuccess

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun syncIngressUser(syncResult: SyncResult) {
        val nets = mutableMapOf<String, DbIngressUser>()
        PortalRestClient.getSync(PortalContract.PATH_Ingress_User, RequestParams(), object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("syncACTIONS: $response")
                for (i in 0 until response!!.length()) {
                    val ingressUser = DbIngressUser.parse(response.optJSONObject(i))
                    nets[ingressUser.name!!] = ingressUser
                }
                val batch = arrayListOf<ContentProviderOperation>()
                val c = contentResolver.query(PortalContract.IngressUser.CONTENT_URI, null, null, null, null)
                c?.moveToFirst()

                var name = ""
                var email = ""
                var inserted_date = "13971213124532"
                var updated_date = "13971213124532"

                var networkIngressUser: DbIngressUser?

                (0 until c!!.count).forEach {
                    syncResult.stats.numEntries++
                    // Create local entry
                    name = c.getString(c.getColumnIndex(PortalContract.IngressUser.COL_name))
                    email = c.getString(c.getColumnIndex(PortalContract.IngressUser.COL_email))
                    inserted_date = c.getString(c.getColumnIndex(PortalContract.IngressUser.COL_inserted_date))
                    updated_date = c.getString(c.getColumnIndex(PortalContract.IngressUser.COL_updated_date))

                    networkIngressUser = nets[name]
                    if (networkIngressUser == null) {
                        //exist in local / not server
                        //send to server
                        val localIngressUser = DbIngressUser(name, email,
                                inserted_date, updated_date)
                        sendToServer(localIngressUser)
                    } else {
                        // does not exist in local
                        nets.remove(name)
                        // update?
                        if (networkIngressUser!!.updated_date?.compareTo(updated_date)!! > 0) {
                            //update local
                            batch.add(ContentProviderOperation.newUpdate(PortalContract.IngressUser.CONTENT_URI)
                                    .withSelection(PortalContract.IngressUser.COL_name + "='" + name + "'", null)
                                    .withValue(PortalContract.IngressUser.COL_email, networkIngressUser!!.email)
                                    .withValue(PortalContract.IngressUser.COL_inserted_date, networkIngressUser!!.inserted_date)
                                    .withValue(PortalContract.IngressUser.COL_updated_date, networkIngressUser!!.updated_date)
                                    .build())
                            syncResult.stats.numUpdates++
                        } else if (networkIngressUser!!.updated_date?.compareTo(updated_date)!! < 0) {
                            // send to server
                            val localIngressUser = DbIngressUser(name, email,
                                    inserted_date, updated_date)
                            sendToServer(localIngressUser)
                        }
                    }//null or not null check done

                    c.moveToNext()
                }
                c.close()

                for (e in nets.values) {
                    Timber.i("$TAG, Scheduling insert ${e.javaClass.simpleName}: $e")
                    batch.add(ContentProviderOperation.newInsert(PortalContract.IngressUser.CONTENT_URI)
                            .withValue(PortalContract.IngressUser.COL_name, e.name)
                            .withValue(PortalContract.IngressUser.COL_email, e.email)
                            .withValue(PortalContract.IngressUser.COL_inserted_date, e.inserted_date)
                            .withValue(PortalContract.IngressUser.COL_updated_date, e.updated_date)
                            .build())

                    syncResult.stats.numInserts++
                }

                contentResolver.applyBatch(PortalContract.CONTENT_AUTHORITY, batch)
                contentResolver.notifyChange(PortalContract.IngressUser.CONTENT_URI,
                        null, false)
            }// end onSuccess

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun syncImageUrls(syncResult: SyncResult) {
        val nets = mutableMapOf<String, DbImageUrl>()
        PortalRestClient.getSync(PortalContract.PATH_IMAGE_URLS, RequestParams(), object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("syncACTIONS: $response")
                for (i in 0 until response!!.length()) {
                    val imageUrl = DbImageUrl.parse(response.optJSONObject(i))
                    nets[imageUrl.url!!] = imageUrl
                }
                val batch = arrayListOf<ContentProviderOperation>()
                val c = contentResolver.query(PortalContract.ImageUrl.CONTENT_URI, null, null, null, null)
                c?.moveToFirst()

                var url = ""
                var uploader = ""
                var inserted_date = "13971213124532"
                var updated_date = "13971213124532"

                var networkImageUrl: DbImageUrl?

                (0 until c!!.count).forEach {
                    syncResult.stats.numEntries++
                    // Create local entry
                    url = c.getString(c.getColumnIndex(PortalContract.ImageUrl.COL_url))
                    uploader = c.getString(c.getColumnIndex(PortalContract.ImageUrl.COL_uploader))
                    inserted_date = c.getString(c.getColumnIndex(PortalContract.ImageUrl.COL_inserted_date))
                    updated_date = c.getString(c.getColumnIndex(PortalContract.ImageUrl.COL_updated_date))

                    networkImageUrl = nets[url]
                    if (networkImageUrl == null) {
                        //exist in local / not server
                        //send to server
                        val localImageUrl = DbImageUrl(url, uploader,
                                inserted_date, updated_date)
                        sendToServer(localImageUrl)
                    } else {
                        // does not exist in local
                        nets.remove(url)
                        // update?
                        if (networkImageUrl!!.updated_date?.compareTo(updated_date)!! > 0) {
                            //update local
                            batch.add(ContentProviderOperation.newUpdate(PortalContract.ImageUrl.CONTENT_URI)
                                    .withSelection(PortalContract.ImageUrl.COL_url + "='" + url + "'", null)
                                    .withValue(PortalContract.ImageUrl.COL_uploader, networkImageUrl!!.uploader)
                                    .withValue(PortalContract.ImageUrl.COL_inserted_date, networkImageUrl!!.inserted_date)
                                    .withValue(PortalContract.ImageUrl.COL_updated_date, networkImageUrl!!.updated_date)
                                    .build())
                            syncResult.stats.numUpdates++
                        } else if (networkImageUrl!!.updated_date?.compareTo(updated_date)!! < 0) {
                            // send to server
                            val localImageUrl = DbImageUrl(url, uploader,
                                    inserted_date, updated_date)
                            sendToServer(localImageUrl)
                        }
                    }//null or not null check done

                    c.moveToNext()
                }
                c.close()

                for (e in nets.values) {
                    Timber.i("$TAG, Scheduling insert ${e.javaClass.simpleName}: $e")
                    batch.add(ContentProviderOperation.newInsert(PortalContract.ImageUrl.CONTENT_URI)
                            .withValue(PortalContract.ImageUrl.COL_url, e.url)
                            .withValue(PortalContract.ImageUrl.COL_uploader, e.uploader)
                            .withValue(PortalContract.ImageUrl.COL_inserted_date, e.inserted_date)
                            .withValue(PortalContract.ImageUrl.COL_updated_date, e.updated_date)
                            .build())

                    syncResult.stats.numInserts++
                }

                contentResolver.applyBatch(PortalContract.CONTENT_AUTHORITY, batch)
                contentResolver.notifyChange(PortalContract.ImageUrl.CONTENT_URI,
                        null, false)
            }// end onSuccess

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun syncPortals(syncResult: SyncResult) {
        val nets = mutableMapOf<String, DbPortal>()
        Timber.d("$TAG, syncPortals")
        PortalRestClient.getSync(PortalContract.PATH_PORTALS, RequestParams(), object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                super.onSuccess(statusCode, headers, response)
                Log.d(TAG, response.toString())
                for (i in 0 until response!!.length()) {
                    val portal = DbPortal.parse(response.optJSONObject(i))
                    nets[portal.id!!] = portal
                }
                val batch = arrayListOf<ContentProviderOperation>()
                val c = contentResolver.query(PortalContract.Portal.CONTENT_URI, null, null, null, null)
                c?.moveToFirst()

                var id = ""
                var title = ""
                var description = ""
                var uploader = ""
                var inserted_date = "13971213124532"
                var updated_date = "13971213124532"

                var networkPortal: DbPortal?

                (0 until c!!.count).forEach {
                    syncResult.stats.numEntries++
                    // Create local entry
                    id = c.getString(c.getColumnIndex(PortalContract.Portal.COL_id))
                    title = c.getString(c.getColumnIndex(PortalContract.Portal.COL_title))
                    description = c.getString(c.getColumnIndex(PortalContract.Portal.COL_description))
                    uploader = c.getString(c.getColumnIndex(PortalContract.Portal.COL_uploader))
                    inserted_date = c.getString(c.getColumnIndex(PortalContract.Portal.COL_inserted_date))
                    updated_date = c.getString(c.getColumnIndex(PortalContract.Portal.COL_updated_date))

                    networkPortal = nets[id]
                    if (networkPortal == null) {
                        //exist in local / not server
                        //send to server
                        val localPortal = DbPortal(id, title, description, uploader,
                                inserted_date, updated_date)
                        sendToServer(localPortal)
                    } else {
                        // does not exist in local
                        nets.remove(id)
                        // update?
                        if (networkPortal!!.updated_date?.compareTo(updated_date)!! > 0) {
                            //update local
                            batch.add(ContentProviderOperation.newUpdate(PortalContract.Portal.CONTENT_URI)
                                    .withSelection(PortalContract.Portal.COL_id + "='" + id + "'", null)
                                    .withValue(PortalContract.Portal.COL_title, networkPortal!!.title)
                                    .withValue(PortalContract.Portal.COL_description, networkPortal!!.description)
                                    .withValue(PortalContract.Portal.COL_uploader, networkPortal!!.uploader)
                                    .withValue(PortalContract.Portal.COL_inserted_date, networkPortal!!.inserted_date)
                                    .withValue(PortalContract.Portal.COL_updated_date, networkPortal!!.updated_date)
                                    .build())
                            syncResult.stats.numUpdates++
                        } else if (networkPortal!!.updated_date?.compareTo(updated_date)!! < 0) {
                            //send to server
                            val localPortal = DbPortal(id, title, description, uploader,
                                    inserted_date, updated_date)
                            sendToServer(localPortal)
                        }
                    }//null or not null check done

                    c.moveToNext()
                }//after cursor loop
                c.close()

                for (e in nets.values) {
                    Timber.i("$TAG, Scheduling insert ${e.javaClass.simpleName}: $e")
                    batch.add(ContentProviderOperation.newInsert(PortalContract.Portal.CONTENT_URI)
                            .withValue(PortalContract.Portal.COL_id, e.id)
                            .withValue(PortalContract.Portal.COL_title, e.title)
                            .withValue(PortalContract.Portal.COL_description, e.description)
                            .withValue(PortalContract.Portal.COL_uploader, e.uploader)
                            .withValue(PortalContract.Portal.COL_inserted_date, e.inserted_date)
                            .withValue(PortalContract.Portal.COL_updated_date, e.updated_date)
                            .build())

                    syncResult.stats.numInserts++
                }

                contentResolver.applyBatch(PortalContract.CONTENT_AUTHORITY, batch)
                contentResolver.notifyChange(PortalContract.Portal.CONTENT_URI,
                        null, false)
            }// end onSuccess

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun sendToServer(local: DbPortalReport) {
        val params = RequestParams()
        params.add(PortalContract.PortalReport.COL_id, local.id)
        params.add(PortalContract.PortalReport.COL_portal_id, local.portal_id)
        params.add(PortalContract.PortalReport.COL_description, local.description)
        params.add(PortalContract.PortalReport.COL_username, local.username)
        params.add(PortalContract.PortalReport.COL_inserted_date, local.inserted_date)
        params.add(PortalContract.PortalReport.COL_updated_date, local.updated_date)

        PortalRestClient.postSync(PortalContract.PATH_portal_location, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("$TAG, $response")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })

    }

    private fun sendToServer(local: DbPortalLocation) {
        val params = RequestParams()
        params.add(PortalContract.PortalLocation.COL_id, local.id)
        params.add(PortalContract.PortalLocation.COL_lat, local.lat.toString())
        params.add(PortalContract.PortalLocation.COL_lon, local.lon.toString())
        params.add(PortalContract.PortalLocation.COL_uploader_name, local.uploader)
        params.add(PortalContract.PortalLocation.COL_inserted_date, local.inserted_date)
        params.add(PortalContract.PortalLocation.COL_updated_date, local.updated_date)

        PortalRestClient.postSync(PortalContract.PATH_portal_location, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("$TAG, $response")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun sendToServer(local: DbPortalImage) {
        val params = RequestParams()
        params.add(PortalContract.PortalImage.COL_id, local.id)
        params.add(PortalContract.PortalImage.COL_portalID, local.portal_id)
        params.add(PortalContract.PortalImage.COL_url, local.url)
        params.add(PortalContract.PortalImage.COL_inserted_date, local.inserted_date)
        params.add(PortalContract.PortalImage.COL_updated_date, local.updated_date)

        PortalRestClient.postSync(PortalContract.PATH_portal_image, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("$TAG, $response")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun sendToServer(local: DbPortalLike) {
        val params = RequestParams()
        params.add(PortalContract.PortalLike.COL_id, local.id)
        params.add(PortalContract.PortalLike.COL_portalID, local.portal_id)
        params.add(PortalContract.PortalLike.COL_username, local.username)
        params.add(PortalContract.PortalLike.COL_inserted_date, local.inserted_date)
        params.add(PortalContract.PortalLike.COL_updated_date, local.updated_date)

        PortalRestClient.postSync(PortalContract.PATH_portal_like, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("$TAG, $response")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun sendToServer(localPortalJuncLocation: DbPortalJuncLocation) {
        val params = RequestParams()
        params.add(PortalContract.PortalJuncLocation.COL_id, localPortalJuncLocation.id)
        params.add(PortalContract.PortalJuncLocation.COL_portalID, localPortalJuncLocation.portal_id)
        params.add(PortalContract.PortalJuncLocation.COL_locationID, localPortalJuncLocation.location_id)
        params.add(PortalContract.PortalJuncLocation.COL_inserted_date, localPortalJuncLocation.inserted_date)
        params.add(PortalContract.PortalJuncLocation.COL_updated_date, localPortalJuncLocation.updated_date)

        PortalRestClient.postSync(PortalContract.PATH_portal_junc_location, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("$TAG, $response")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun sendToServer(localIngressUser: DbIngressUser) {
        val params = RequestParams()
        params.add(PortalContract.IngressUser.COL_name, localIngressUser.name)
        params.add(PortalContract.IngressUser.COL_email, localIngressUser.email)
        params.add(PortalContract.IngressUser.COL_inserted_date, localIngressUser.inserted_date)
        params.add(PortalContract.IngressUser.COL_updated_date, localIngressUser.updated_date)

        PortalRestClient.postSync(PortalContract.PATH_Ingress_User, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("$TAG, $response")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun sendToServer(imageUrl: DbImageUrl) {
        val params = RequestParams()
        params.add(PortalContract.ImageUrl.COL_url, imageUrl.url)
        params.add(PortalContract.ImageUrl.COL_uploader, imageUrl.uploader)
        params.add(PortalContract.ImageUrl.COL_inserted_date, imageUrl.inserted_date)
        params.add(PortalContract.ImageUrl.COL_updated_date, imageUrl.updated_date)

        PortalRestClient.postSync(PortalContract.PATH_IMAGE_URLS, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("$TAG, $response")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    private fun sendToServer(localPortal: DbPortal) {
        val params = RequestParams()
        params.add(PortalContract.Portal.COL_id, localPortal.id)
        params.add(PortalContract.Portal.COL_title, localPortal.title)
        params.add(PortalContract.Portal.COL_description, localPortal.description)
        params.add(PortalContract.Portal.COL_uploader, localPortal.uploader)
        params.add(PortalContract.Portal.COL_inserted_date, localPortal.inserted_date)
        params.add(PortalContract.Portal.COL_updated_date, localPortal.updated_date)

        PortalRestClient.postSync(PortalContract.PATH_PORTALS, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("$TAG, $response")
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Timber.d("$TAG, $responseString")
            }
        })
    }

    companion object {
        private val TAG = SyncAdapter::class.java.simpleName
        val SYNC_FINISHED = "SYNC_FINISHED"
        fun performSync() {
            Timber.d("SyncAdapter: inside performSync")
            val b = Bundle()
            b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true)
            b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true)
            ContentResolver.requestSync(AccountGeneral.account, PortalContract.CONTENT_AUTHORITY, b)
        }
    }
}
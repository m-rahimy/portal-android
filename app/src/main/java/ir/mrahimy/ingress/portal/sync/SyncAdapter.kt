package ir.mrahimy.ingress.portal.sync

import android.accounts.Account
import android.content.*
import android.os.Bundle
import android.os.RemoteException
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import ir.mrahimy.ingress.portal.dbmodel.DbPortal
import ir.mrahimy.ingress.portal.net.PortalRestClient
import timber.log.Timber
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import android.content.Intent
import android.content.ContentProviderOperation
import ir.mrahimy.ingress.portal.dbmodel.DbImageUrl
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

    override fun onPerformSync(account: Account, bundle: Bundle, s: String, contentProviderClient: ContentProviderClient, syncResult: SyncResult) {
        try {
            sync(syncResult)
        } catch (ex: IOException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numParseExceptions++
        } catch (e: RemoteException) {
            Timber.e(TAG, "Error synchronizing!", e)
            syncResult.stats.numAuthExceptions++
        } catch (e: OperationApplicationException) {
            syncResult.stats.numAuthExceptions++
        }

        val i = Intent(SYNC_FINISHED)
        context.sendBroadcast(i)
    }

    private fun sync(syncResult: SyncResult) {
        try {
            syncPortals(syncResult)
        } catch (ex: IOException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numParseExceptions++
        } catch (e: RemoteException) {
            Timber.e(TAG, "Error synchronizing!", e)
            syncResult.stats.numAuthExceptions++
        } catch (e: OperationApplicationException) {
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncImageUrls(syncResult)
        } catch (ex: IOException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numParseExceptions++
        } catch (e: RemoteException) {
            Timber.e(TAG, "Error synchronizing!", e)
            syncResult.stats.numAuthExceptions++
        } catch (e: OperationApplicationException) {
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncIngressUser(syncResult)
        } catch (ex: IOException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numParseExceptions++
        } catch (e: RemoteException) {
            Timber.e(TAG, "Error synchronizing!", e)
            syncResult.stats.numAuthExceptions++
        } catch (e: OperationApplicationException) {
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncPortalJuncLoc(syncResult)
        } catch (ex: IOException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numParseExceptions++
        } catch (e: RemoteException) {
            Timber.e(TAG, "Error synchronizing!", e)
            syncResult.stats.numAuthExceptions++
        } catch (e: OperationApplicationException) {
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncLikes(syncResult)
        } catch (ex: IOException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numParseExceptions++
        } catch (e: RemoteException) {
            Timber.e(TAG, "Error synchronizing!", e)
            syncResult.stats.numAuthExceptions++
        } catch (e: OperationApplicationException) {
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncImages(syncResult)
        } catch (ex: IOException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numParseExceptions++
        } catch (e: RemoteException) {
            Timber.e(TAG, "Error synchronizing!", e)
            syncResult.stats.numAuthExceptions++
        } catch (e: OperationApplicationException) {
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncPortalLocation(syncResult)
        } catch (ex: IOException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numParseExceptions++
        } catch (e: RemoteException) {
            Timber.e(TAG, "Error synchronizing!", e)
            syncResult.stats.numAuthExceptions++
        } catch (e: OperationApplicationException) {
            syncResult.stats.numAuthExceptions++
        }

        try {
            syncPortalReport(syncResult)
        } catch (ex: IOException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numIoExceptions++
        } catch (ex: JSONException) {
            Timber.e(TAG, "Error synchronizing!", ex)
            syncResult.stats.numParseExceptions++
        } catch (e: RemoteException) {
            Timber.e(TAG, "Error synchronizing!", e)
            syncResult.stats.numAuthExceptions++
        } catch (e: OperationApplicationException) {
            syncResult.stats.numAuthExceptions++
        }
    }

    private fun syncPortalReport(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncPortalLocation(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncImages(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncLikes(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncPortalJuncLoc(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncIngressUser(syncResult: SyncResult) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun syncImageUrls(syncResult: SyncResult) {
        val nets = mutableMapOf<String, DbImageUrl>()
        PortalRestClient.get(PortalContract.PATH_IMAGE_URLS, RequestParams(), object : JsonHttpResponseHandler() {
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
                    // Create local portal entry
                    url = c.getString(c.getColumnIndex(PortalContract.ImageUrl.COL_url))
                    uploader = c.getString(c.getColumnIndex(PortalContract.ImageUrl.COL_uploader))
                    inserted_date = c.getString(c.getColumnIndex(PortalContract.ImageUrl.COL_inserted_date))
                    updated_date = c.getString(c.getColumnIndex(PortalContract.ImageUrl.COL_updated_date))

                    networkImageUrl = nets[url]
                    if (networkImageUrl == null) {
                        //exist in local but not server
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
                //after cursor loop

                /*
                COL_url = "url"
                COL_uploader = "uploader"
                COL_inserted_date = "inserted_date"
                COL_updated_date = "updated_date"
                 */

                for (e in nets.values) {
                    Timber.i("$TAG, Scheduling insert RowTitle: $e")
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
        PortalRestClient.get(PortalContract.PATH_PORTALS, RequestParams(), object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                super.onSuccess(statusCode, headers, response)
                Timber.d("syncACTIONS: $response")
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
                    // Create local portal entry
                    id = c.getString(c.getColumnIndex(PortalContract.Portal.COL_id))
                    title = c.getString(c.getColumnIndex(PortalContract.Portal.COL_title))
                    description = c.getString(c.getColumnIndex(PortalContract.Portal.COL_description))
                    uploader = c.getString(c.getColumnIndex(PortalContract.Portal.COL_uploader))
                    inserted_date = c.getString(c.getColumnIndex(PortalContract.Portal.COL_inserted_date))
                    updated_date = c.getString(c.getColumnIndex(PortalContract.Portal.COL_updated_date))

                    networkPortal = nets[id]
                    if (networkPortal == null) {
                        //exist in local but not server
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

                /*
                COL_id = "id"
                COL_title = "title"
                COL_description = "description"
                COL_uploader = "uploader"
                COL_inserted_date = "inserted_date"
                COL_updated_date = "updated_date"
                 */

                for (e in nets.values) {
                    Timber.i("$TAG, Scheduling insert RowTitle: $e")
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

    private fun sendToServer(imageUrl: DbImageUrl) {
        val params = RequestParams()
        params.add(PortalContract.ImageUrl.COL_url, imageUrl.url)
        params.add(PortalContract.ImageUrl.COL_uploader, imageUrl.uploader)
        params.add(PortalContract.ImageUrl.COL_inserted_date, imageUrl.inserted_date)
        params.add(PortalContract.ImageUrl.COL_updated_date, imageUrl.updated_date)

        PortalRestClient.post(PortalContract.PATH_IMAGE_URLS, params, object : JsonHttpResponseHandler() {
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

        PortalRestClient.post(PortalContract.PATH_PORTALS, params, object : JsonHttpResponseHandler() {
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
            val b = Bundle()
            b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true)
            b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true)
            ContentResolver.requestSync(AccountGeneral.account, PortalContract.CONTENT_AUTHORITY, b)
        }
    }
}
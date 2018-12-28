package ir.mrahimy.ingress.portal.view.fragments

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.content.ContentProviderOperation
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.net.PortalRestClient
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import com.loopj.android.http.RequestParams
import ir.mrahimy.ingress.portal.dbmodel.*
import ir.mrahimy.ingress.portal.net.GetAddressHandler
import ir.mrahimy.ingress.portal.net.UploadResponseHandler
import ir.mrahimy.ingress.portal.sync.PortalContract
import ir.mrahimy.ingress.portal.sync.SyncAdapter
import ir.mrahimy.ingress.portal.util.*
import ir.mrahimy.ingress.portal.view.ChooseLocationActivity
import java.io.*
import java.lang.Exception
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddPortalFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddPortalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPortalFragment : Fragment() {
    lateinit var latTxt: TextView
    lateinit var lonTxt: TextView
    lateinit var addressTxt: TextView
    lateinit var image1: ImageView
    lateinit var image2: ImageView
    lateinit var image3: ImageView
    lateinit var descriptionTxt: EditText
    lateinit var titleTxt: EditText
    lateinit var sendButton: Button
    var glovbalI: Int = 0
    var zoom: Double? = 5.5

    val portalImageIds = mutableListOf<String?>()
    var hasLatLon = false
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_portal, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val chooseLocationButton = view.findViewById<Button>(R.id.add_portal_location_button)
        chooseLocationButton.setOnClickListener {
            if (hasLatLon)
                goToChooseLocation(latTxt.text.toString().toDouble()
                        , lonTxt.text.toString().toDouble(), zoom)
            else goToChooseLocation(0.0, 0.0, zoom)
        }

        latTxt = view.findViewById(R.id.add_portal_location_text_lat)
        lonTxt = view.findViewById(R.id.add_portal_location_text_lon)
        addressTxt = view.findViewById(R.id.add_portal_address_text)
        descriptionTxt = view.findViewById(R.id.add_portal_description)
        titleTxt = view.findViewById(R.id.add_portal_title)
        titleTxt.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(p0: Editable?) {
                sendButton.isEnabled = p0?.length!! > 3
            }
        })

        image1 = view.findViewById<ImageView>(R.id.add_portal_image1)
        image1.setOnClickListener {
            showChooseDialogue(1)
        }

        image2 = view.findViewById<ImageView>(R.id.add_portal_image2)
        image2.setOnClickListener {
            showChooseDialogue(2)
        }

        image3 = view.findViewById<ImageView>(R.id.add_portal_image3)
        image3.setOnClickListener {
            showChooseDialogue(3)
        }

        sendButton = view.findViewById<Button>(R.id.add_portal_send_button)
        sendButton.setOnClickListener {
            beginSavePortalProcess()
        }

        view.findViewById<Button>(R.id.add_portal_clear_button).setOnClickListener {
            clearFields()
        }

    }

    private fun clearFields() {
        portalImageIds.clear()
    }

    private fun beginSavePortalProcess() {
        /*
            PortalLocation
            ImageUrls
            Portals
            PortalImages
            PortalJuncLoc
         */
        //TODO: get content resolver, send it to local database
        val contentResolver = activity!!.applicationContext.contentResolver
        val batch = arrayListOf<ContentProviderOperation>()
        Log.d(TAG, " date format ${Date().toMySqlformat()}") //"2018-12-07 09:43:21"
        val date = Date().toMySqlformat()

        val portalLocation = DbPortalLocation()
        val imageUrl = mutableListOf<DbImageUrl>() //TODO
        val portal = DbPortal()
        val portalImage = mutableListOf<DbPortalImage>() //TODO
        val portalJlocation = DbPortalJuncLocation()

        portalLocation.id = UUID.randomUUID().toString()
        portalLocation.uploader = "sargeVincent"//TODO: get from prefs
        portalLocation.lat = latTxt.text.toString().toDouble()
        portalLocation.lon = lonTxt.text.toString().toDouble()
        portalLocation.address = addressTxt.text.toString()
        portalLocation.inserted_date = date
        portalLocation.updated_date = date

        portal.id = UUID.randomUUID().toString()
        portal.title = titleTxt.text.toString()
        portal.description = descriptionTxt.text.toString()
        portal.uploader = "sargeVincent" // TODO
        portal.inserted_date = date
        portal.updated_date = date

        batch.add(ContentProviderOperation.newInsert(PortalContract.PortalLocation.CONTENT_URI)
                .withValue(PortalContract.PortalLocation.COL_id, portalLocation.id)
                .withValue(PortalContract.PortalLocation.COL_lat, portalLocation.lat)
                .withValue(PortalContract.PortalLocation.COL_lon, portalLocation.lon)
                .withValue(PortalContract.PortalLocation.COL_address, portalLocation.address)
                .withValue(PortalContract.PortalLocation.COL_uploader_name, portalLocation.uploader)
                .withValue(PortalContract.PortalLocation.COL_inserted_date, portalLocation.inserted_date)
                .withValue(PortalContract.PortalLocation.COL_updated_date, portalLocation.updated_date)
                .build())

        batch.add(ContentProviderOperation.newInsert(PortalContract.Portal.CONTENT_URI)
                .withValue(PortalContract.Portal.COL_id, portal.id)
                .withValue(PortalContract.Portal.COL_title, portal.title)
                .withValue(PortalContract.Portal.COL_description, portal.description)
                .withValue(PortalContract.Portal.COL_uploader, portal.uploader)
                .withValue(PortalContract.Portal.COL_inserted_date, portal.inserted_date)
                .withValue(PortalContract.Portal.COL_updated_date, portal.updated_date)
                .build())

        //TODO : others
        contentResolver.applyBatch(PortalContract.CONTENT_AUTHORITY, batch)
        contentResolver.notifyChange(PortalContract.PortalLocation.CONTENT_URI,
                null, false)

        //TODO: checkInternet {request sync}
        SyncAdapter.performSync()

    }

    private fun goToChooseLocation(lat: Double, lon: Double, zoom: Double? = 5.5) {
        val i = Intent(activity, ChooseLocationActivity::class.java)
        i.putExtra("lat", lat)
        i.putExtra("lon", lon)
        i.putExtra("zoom", zoom)
        startActivityForResult(i, CODE_ADD_LOCATION)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        private val GALLERY = 1001
        private val GALLERY1 = 10011
        private val GALLERY2 = 10012
        private val GALLERY3 = 10013
        private val CAMERA = 1002
        private val CAMERA1 = 10021
        private val CAMERA2 = 10022
        private val CAMERA3 = 10023
        private val IMAGE_DIRECTORY = "portal_images"
        private val CAMERA_PERMISSION_CODE = 20001
        val CODE_ADD_LOCATION = 1001

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPortalFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): AddPortalFragment {
            val fragment = AddPortalFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }


        val TAG = AddPortalFragment::class.java.simpleName
        val TITLE: CharSequence? = "Suggesting Portals"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_CANCELED) {
            return
        }

        when (requestCode) {
            CODE_ADD_LOCATION -> handleLocationChoosing(data)
            GALLERY1, GALLERY2, GALLERY3 -> handleGalleryResponse(data, requestCode)
            CAMERA1, CAMERA2, CAMERA3 -> handleCameraResponse(data, requestCode)
        }
    }

    private fun handleLocationChoosing(data: Intent?) {
        val lat = data?.getDoubleExtra("lat", 0.0).toString()
        val lon = data?.getDoubleExtra("lon", 0.0).toString()
        zoom = data?.getDoubleExtra("zoom", 5.5)

        latTxt.text = lat
        lonTxt.text = lon
        hasLatLon = true
        getAddress(lat, lon)
    }

    private fun handleCameraResponse(data: Intent?, requestCode: Int) {
        val thumbnail = data?.extras?.get("data") as Bitmap
        val path = saveImage(thumbnail)
        //TODO: upload
        uploadImage(path, { setImageBitmapToImageView(thumbnail, requestCode) },
                { showUploadFailToast() }
        )
    }

    private fun handleGalleryResponse(data: Intent?, requestCode: Int) {
        if (data == null) return
        var success = false
        val contentURI = data.data
        var bitmap =
                try {
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
                    Bitmap.createBitmap(0, 0, Bitmap.Config.ALPHA_8)
                }

        val path = try {
            //upload
            ImageFilePath.getPath(activity, contentURI)

        } catch (e: IOException) {
            e.printStackTrace()
            "none"
        }

        if (bitmap == null || bitmap.height < 1 || bitmap.width < 1 ||
                path == null || path == "none") {
            return
        }

        Log.d("UPLOAD_DEBUG", "path = $path")
        activity?.checkInternet({
            uploadImage(path, { setImageBitmapToImageView(bitmap, requestCode) },
                    { showUploadFailToast() }
            )
        }, { activity?.toastNoInternet() })
    }

    private fun getAddress(lat: String, lon: String) {
        PortalRestClient.getAddressForPoint(lat, lon, GetAddressHandler(addressTxt))
    }

    fun setImageBitmapToImageView(image: Bitmap, requestCode: Int) {
        when (requestCode) {
            CAMERA1, GALLERY1 -> image1.setImageBitmap(image)
            CAMERA2, GALLERY2 -> image2.setImageBitmap(image)
            CAMERA3, GALLERY3 -> image3.setImageBitmap(image)
        }
    }

    private fun uploadImage(path: String?, onSuccess: () -> Unit, onFailure: () -> Unit) {
        Log.d("UPLOAD_DEBUG", "uploadImage() path = $path")
        val params = RequestParams()
        var success = false
        try {
            val file = File(path)
            Log.d("UPLOAD_DEBUG", "file path is ${file.absolutePath}")
            params.put("image", file)
            success = true

        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d("UPLOAD_DEBUG", "success is $success")

        if (success) {
            PortalRestClient.uploadImage(params,
                    UploadResponseHandler(portalImageIds, onSuccess, onFailure))
        }
    }

    fun showUploadFailToast() {
        Toast.makeText(this.activity, "Failed to upload !!", Toast.LENGTH_LONG)
    }

    fun showChooseDialogue(i: Int) {
        val pictureDialog = AlertDialog.Builder(activity!!)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary(i)
                1 -> {//TODO: CAMERA PERMISSION
                    if (activity!!.hasCameraPermission()) {
                        takePhotoFromCamera(i)
                    } else {
                        glovbalI = i
                        activity?.requestCameraPermission(CAMERA_PERMISSION_CODE)
                    }
                }
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary(i: Int) {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, Integer.parseInt(GALLERY.toString() + i.toString()))
    }

    private fun takePhotoFromCamera(i: Int) {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, Integer.parseInt(CAMERA.toString() + i.toString()))
    }

    fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val wallpaperDirectory = File(
                Environment.getExternalStorageDirectory().toString() +
                        File.separator + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d(TAG, wallpaperDirectory.absolutePath)
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try {
            val f = File(wallpaperDirectory, "${Calendar.getInstance()
                    .timeInMillis}.jpg")
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(activity,
                    arrayOf(f.path),
                    arrayOf("image/jpeg"), null)
            fo.close()
            Log.d(TAG, "File Saved " + f.absolutePath)
            return f.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> handleCameraPermissionResult(permissions, grantResults)
        }
    }

    private fun handleCameraPermissionResult(permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePhotoFromCamera(glovbalI)
        }else{
            handlePermissionDenial("CAMERA")
        }
    }

    private fun handlePermissionDenial(s: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow $s access permissions",
                                    DialogInterface.OnClickListener { p0, p1 ->
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            activity?.requestCameraPermission(CAMERA_PERMISSION_CODE)
                                        }
                                    })
                                            
                        }
                    }
    }

    private fun showMessageOKCancel(s: String, onClickListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(activity!!)
                .setMessage(s)
                .setPositiveButton("OK", onClickListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }


}// Required empty public constructor

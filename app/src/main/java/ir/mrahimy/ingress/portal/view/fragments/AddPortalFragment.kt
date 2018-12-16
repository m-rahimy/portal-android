package ir.mrahimy.ingress.portal.view.fragments

import android.app.Activity.RESULT_CANCELED
import android.content.Context
import android.content.Intent
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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header

import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.net.PortalRestClient
import org.json.JSONObject
import java.io.IOException
import android.media.MediaScannerConnection
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
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
    lateinit var add_portal_location_text_lat: TextView
    lateinit var add_portal_location_text_lon: TextView
    lateinit var add_portal_address_text: TextView
    lateinit var add_portal_image1: ImageView
    lateinit var add_portal_image2: ImageView
    lateinit var add_portal_image3: ImageView
    var zoom: Double? = 5.5

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
        // Inflate the layout for this fragment
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
                goToChooseLocation(add_portal_location_text_lat.text.toString().toDouble()
                        , add_portal_location_text_lon.text.toString().toDouble(), zoom)
            else goToChooseLocation(0.0, 0.0, zoom)
        }

        add_portal_location_text_lat = view.findViewById(R.id.add_portal_location_text_lat)
        add_portal_location_text_lon = view.findViewById(R.id.add_portal_location_text_lon)
        add_portal_address_text = view.findViewById(R.id.add_portal_address_text)

        add_portal_image1 = view.findViewById<ImageView>(R.id.add_portal_image1)
        add_portal_image1.setOnClickListener {
            showChooseDialogue(1)
        }

        add_portal_image2 = view.findViewById<ImageView>(R.id.add_portal_image2)
        add_portal_image2.setOnClickListener {
            showChooseDialogue(2)
        }

        add_portal_image3 = view.findViewById<ImageView>(R.id.add_portal_image3)
        add_portal_image3.setOnClickListener {
            showChooseDialogue(3)
        }
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

        val CODE_ADD_LOCATION = 1001

        val TAG = AddPortalFragment::class.java.simpleName
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            return
        }
        if (requestCode == CODE_ADD_LOCATION) {
            //Log.d(TAG, data?.getDoubleExtra("lat", 0.0).toString())
            //Log.d(TAG, data?.getDoubleExtra("lon", 0.0).toString())
            val lat = data?.getDoubleExtra("lat", 0.0).toString()
            val lon = data?.getDoubleExtra("lon", 0.0).toString()
            zoom = data?.getDoubleExtra("zoom", 5.5)

            add_portal_location_text_lat.text = lat
            add_portal_location_text_lon.text = lon
            hasLatLon = true

            PortalRestClient.getAddressForPoint(lat, lon, object : JsonHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                    super.onSuccess(statusCode, headers, response)
                    add_portal_address_text.text = response?.optString("address")
                }

                override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseString: String?, throwable: Throwable?) {
                    super.onFailure(statusCode, headers, responseString, throwable)
                }
            })

        } else if (requestCode == GALLERY1 || requestCode == GALLERY2 || requestCode == GALLERY3) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)
                    //val path = saveImage(bitmap)
                    //Toast.makeText(activity, "Image Saved! PATH=$path", Toast.LENGTH_SHORT).show()
                    //TODO: upload
                    if (requestCode == GALLERY1) add_portal_image1.setImageBitmap(bitmap)
                    if (requestCode == GALLERY2) add_portal_image2.setImageBitmap(bitmap)
                    if (requestCode == GALLERY3) add_portal_image3.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }

        } else if (requestCode == CAMERA1 || requestCode == CAMERA2 || requestCode == CAMERA3) {
            val thumbnail = data?.extras?.get("data") as Bitmap
            //TODO: upload
            if (requestCode == CAMERA1) add_portal_image1.setImageBitmap(thumbnail)
            if (requestCode == CAMERA2) add_portal_image2.setImageBitmap(thumbnail)
            if (requestCode == CAMERA3) add_portal_image3.setImageBitmap(thumbnail)
            val path = saveImage(thumbnail)
            Toast.makeText(activity, "Image Saved! $path", Toast.LENGTH_SHORT).show()
        }
    }

    fun showChooseDialogue(i: Int) {
        val pictureDialog = AlertDialog.Builder(activity!!)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary(i)
                1 -> takePhotoFromCamera(i)
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
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
                Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try {
            val f = File(wallpaperDirectory, "${Calendar.getInstance()
                    .getTimeInMillis()} .jpg")
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(activity,
                    arrayOf(f.getPath()),
                    arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }


}// Required empty public constructor

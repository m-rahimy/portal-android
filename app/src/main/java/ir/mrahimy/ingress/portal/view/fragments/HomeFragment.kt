package ir.mrahimy.ingress.portal.view.fragments

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.database.CursorIndexOutOfBoundsException
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.adapter.PortalAdapter
import ir.mrahimy.ingress.portal.dbmodel.DbPortal
import ir.mrahimy.ingress.portal.model.Portal
import ir.mrahimy.ingress.portal.sync.PortalContract
import ir.mrahimy.ingress.portal.util.getFullData
import ir.mrahimy.ingress.portal.view.MainActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var portalCursor: Cursor
    private var mContentResolver: ContentResolver? = null

    private val databasePortalList = mutableListOf<DbPortal>()
    private lateinit var portalList: List<Portal>

    private val allObserver = AllObserver()

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        val portalCursor = activity!!.applicationContext.contentResolver.query(
                PortalContract.Portal.CONTENT_URI, null, null, null, null
        )
        portalCursor?.moveToFirst()
        Log.d(TAG, " cursor count :${portalCursor?.count}")
        do {
            try{
                Log.d(TAG, " date format :${portalCursor?.getString(
                        portalCursor?.getColumnIndex(PortalContract.Portal.COL_updated_date))}")
            }catch (e:CursorIndexOutOfBoundsException){
                e.printStackTrace()
            }
        } while (portalCursor.moveToNext())
        portalCursor?.close()
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onStart() {
        super.onStart()
        refreshData()
        activity!!.contentResolver.registerContentObserver(
                PortalContract.Portal.CONTENT_URI,
                true,
                allObserver
        )
        activity!!.contentResolver.registerContentObserver(
                PortalContract.ImageUrl.CONTENT_URI,
                true,
                allObserver
        )
        activity!!.contentResolver.registerContentObserver(
                PortalContract.IngressUser.CONTENT_URI,
                true,
                allObserver
        )
        activity!!.contentResolver.registerContentObserver(
                PortalContract.PortalJuncLocation.CONTENT_URI,
                true,
                allObserver
        )
        activity!!.contentResolver.registerContentObserver(
                PortalContract.PortalLike.CONTENT_URI,
                true,
                allObserver
        )
        activity!!.contentResolver.registerContentObserver(
                PortalContract.PortalImage.CONTENT_URI,
                true,
                allObserver
        )
        activity!!.contentResolver.registerContentObserver(
                PortalContract.PortalLocation.CONTENT_URI,
                true,
                allObserver
        )
        activity!!.contentResolver.registerContentObserver(
                PortalContract.PortalReport.CONTENT_URI,
                true,
                allObserver
        )
    }

    override fun onStop() {
        super.onStop()
        activity!!.contentResolver.unregisterContentObserver(allObserver)
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
        val TAG = "PortalList"
        val TITLE: CharSequence? = "Portals List"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    private fun refreshData() {
        portalCursor = activity!!.applicationContext.contentResolver.query(
                PortalContract.Portal.CONTENT_URI, null, null,
                null, null)!!
        portalCursor.moveToFirst()
        //Timber.d("$TAG cursor count :${portalCursor.count}")

        var success = false
        try {
            databasePortalList.clear()
            databasePortalList.addAll(DbPortal.parseAll(portalCursor))
            success = true
        } catch (e: CursorIndexOutOfBoundsException) {
            e.printStackTrace()
        }

        //Timber.d("$TAG, $databasePortalList")

        portalCursor.moveToFirst()
        //Timber.d("$TAG cursor count :${portalCursor.count}")
        assert(success)
        populateListData()

    }

    private fun populateListData() {
        var mustShowSyncNeededDialog = false
        try {
            portalList = databasePortalList.getFullData(activity!!.applicationContext.contentResolver)
            //Timber.d("$TAG FullData: ${portalList[0].reports!![0].description}")
            //Timber.d("$TAG FullData portalList SIZE: ${portalList.size}")
            //Timber.d("$TAG FullData databasePortalList SIZE: ${databasePortalList.size}")
            //TODO: load into rec view
            home_rec_view.adapter = PortalAdapter(activity!!, portalList)
            home_rec_view.layoutManager = LinearLayoutManager(activity)
        } catch (e: CursorIndexOutOfBoundsException) {
            e.printStackTrace()
            mustShowSyncNeededDialog = true
        }

        if (mustShowSyncNeededDialog) {
            showSyncNeededDialog()
        }

    }

    private fun showSyncNeededDialog() {
        (activity as MainActivity).showSyncNeededDialog()
    }

    private inner class AllObserver public constructor()
        : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            //Timber.d("$TAG, onChange")
            refreshData()
        }
    }
}

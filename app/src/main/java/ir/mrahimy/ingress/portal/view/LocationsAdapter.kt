package ir.mrahimy.ingress.portal.view

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ir.map.sdk_services.ServiceHelper
import ir.map.sdk_services.models.MaptexError
import ir.map.sdk_services.models.MaptexReverse
import ir.map.sdk_services.models.base.ResponseListener
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.model.ParcelablePortalJuncLocation

class LocationsAdapter(private val context: Context,
                       private val data: List<ParcelablePortalJuncLocation>) :
        RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.portal_location_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pjl = data[position]
        holder.user.text = pjl.uploader
        val staticMapListener = object : ResponseListener<Bitmap> {
            override fun onSuccess(response: Bitmap) {
                holder.image.setImageBitmap(response)
            }

            override fun onError(error: MaptexError) {

            }
        }

        val addressListener = object : ResponseListener<MaptexReverse> {
            override fun onSuccess(response: MaptexReverse?) {
                holder.address.text = response?.addressCompact
            }

            override fun onError(error: MaptexError?) {

            }
        }


        ServiceHelper().getStaticMap(pjl.lat!!, pjl.lon!!, 10, staticMapListener)//z:12
        ServiceHelper().getReverseGeoInfo(pjl.lat!!, pjl.lon!!, addressListener)

        holder.image.setOnClickListener {
            (context as LocationsActivity).goToLocationActivity(pjl)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val image = v.findViewById<ImageView>(R.id.portal_j_location_card_static_image)
        val address = v.findViewById<TextView>(R.id.portal_j_location_card_address)
        val user = v.findViewById<TextView>(R.id.portal_j_location_card_user)
    }
}

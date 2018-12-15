package ir.mrahimy.ingress.portal.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.net.PortalRestClient
import ir.mrahimy.ingress.portal.model.ParcelablePortalImage
import ir.mrahimy.ingress.portal.view.ImagesViewActivity


class PortalImageAdapter(private val activity: ImagesViewActivity,
                         private val data: List<ParcelablePortalImage>?) :
        RecyclerView.Adapter<PortalImageAdapter.ViewHolder>() {

    companion object {
        val TAG: String = PortalImageAdapter::class.java.simpleName
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(activity)
                .inflate(R.layout.portal_image_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pImg = data?.get(position)
        val url = PortalRestClient.IMAGE_PATH_BASE + pImg?.image_url
        Picasso.get()
                .load(url)
                //.resize(50, 50)
                //.centerCrop()
                .into(holder.portalImageCardImage)
        //holder.portalImageCardImage.setImageURI(Uri.parse(url))
        holder.portalImageCardUsername.text = pImg?.image_uploader
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val portalImageCardImage: ImageView = v.findViewById(R.id.portal_image_card_image)
        val portalImageCardUsername: TextView = v.findViewById(R.id.portal_image_card_username)
    }
}

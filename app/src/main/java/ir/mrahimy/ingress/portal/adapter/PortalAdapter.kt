package ir.mrahimy.ingress.portal.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.model.Portal
import ir.mrahimy.ingress.portal.net.PortalRestClient
import timber.log.Timber

class PortalAdapter(private val context: Context,
                    private val dataList: List<Portal>) :
        RecyclerView.Adapter<PortalAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val portal = dataList[position]
        //Timber.d("$TAG ${portal.imageUrls?.get(0)?.image?.url} by ${portal.imageUrls?.get(0)?.image?.uploader?.name}")
        //TODO: put data to view
        holder.portalCardImageCount.text = portal.imageUrls?.count().toString()
        holder.portalCardName.text = portal.title
        holder.portalCardDesc.text = portal.description
        //TODO: toggle like if liked by user
        //TODO: put image
        val url = when (portal.imageUrls!!.size) {
            0 -> PortalRestClient.IMAGE_PATH_EMPTY
            1 -> PortalRestClient.IMAGE_PATH_BASE + portal.imageUrls!![0].image?.url
            else -> PortalRestClient.IMAGE_PATH_EMPTY // TODO: find more liked images
        }

        holder.mainImage.setImageURI(Uri.parse(url))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.portal_card, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainImage = itemView.findViewById<SimpleDraweeView>(R.id.portal_card_main_image)
        var portalCardImageCount = itemView.findViewById<TextView>(R.id.portal_card_image_count)
        var portalCardName = itemView.findViewById<TextView>(R.id.portal_card_name)
        var portalCardDesc = itemView.findViewById<TextView>(R.id.portal_card_desc)
        var portalCardLikeButton = itemView.findViewById<TextView>(R.id.portal_card_like_button)
    }

    companion object {
        val TAG = PortalAdapter::class.java.simpleName
    }
}
package ir.mrahimy.ingress.portal.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.model.Portal
import ir.mrahimy.ingress.portal.net.PortalRestClient
import ir.mrahimy.ingress.portal.view.MainActivity

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
        // put image
        val url = when (portal.imageUrls?.size) {
            0 -> PortalRestClient.IMAGE_PATH_EMPTY
            1 -> PortalRestClient.IMAGE_PATH_BASE + portal.imageUrls!![0].image?.url
            // TODO: find images with more likes:
            else -> PortalRestClient.IMAGE_PATH_BASE + portal.imageUrls!![0].image?.url
        }
        Picasso.get()
                .load(url)
                .resize(50, 50)
                .centerCrop()
                .into(holder.mainImage)
        //holder.mainImage.setImageURI(Uri.parse(url))

        holder.mainImage.setOnClickListener {
            (context as MainActivity).goToImagesActivity(portal.imageUrls)
        }
        ////////////////////////////image done
        val likes = portal.likes?.filter { it.like == true }?.size
        holder.portalCardLikes.text = likes.toString()

        holder.portalCardPosButton.setOnClickListener {
            (context as MainActivity).goToLocationActivity(portal.locations)
            //(context as MainActivity).goToStaticLocationActivity(portal.locations)
        }

        holder.portalCardAddress.text =
                if (portal.locations?.isNotEmpty()!!)
                    portal.locations?.get(0)?.location?.address
                else "بدون آدرس"

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.portal_card, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainImage = itemView.findViewById<ImageView>(R.id.portal_card_main_image)
        var portalCardImageCount = itemView.findViewById<TextView>(R.id.portal_card_image_count)
        var portalCardName = itemView.findViewById<TextView>(R.id.portal_card_name)
        var portalCardDesc = itemView.findViewById<TextView>(R.id.portal_card_desc)
        var portalCardAddress = itemView.findViewById<TextView>(R.id.portal_card_address)
        var portalCardLikeButton = itemView.findViewById<Button>(R.id.portal_card_like_button)
        var portalCardPosButton = itemView.findViewById<Button>(R.id.portal_card_pos_button)
        var portalCardPicsButton = itemView.findViewById<Button>(R.id.portal_card_pics_button)
        var portalCardReportButton = itemView.findViewById<Button>(R.id.portal_card_report_button)
        var portalCardLikes = itemView.findViewById<TextView>(R.id.portal_card_likes)
    }

    companion object {
        val TAG = PortalAdapter::class.java.simpleName
    }
}
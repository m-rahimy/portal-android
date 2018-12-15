package ir.mrahimy.ingress.portal.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.adapter.PortalImageAdapter
import ir.mrahimy.ingress.portal.model.ParcelablePortalImage
import kotlinx.android.synthetic.main.activity_images_view.*

class ImagesViewActivity : AppCompatActivity() {

    companion object {
        val TAG = ImagesViewActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_view)

        val images = intent?.extras?.getParcelableArray("portal_images")?.toList()
        val imList = mutableListOf<ParcelablePortalImage>()
        images?.forEach {
            //Timber.d("$TAG, $it")
            imList.add(it as ParcelablePortalImage)
            val i2 = it.copy()
            i2.image_url = "/img/z.png"
            imList.add(i2)
        }

        images_rec_view.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(images_rec_view)

        images_rec_view.adapter = PortalImageAdapter(this, imList)

    }
}

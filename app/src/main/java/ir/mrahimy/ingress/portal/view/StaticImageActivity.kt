package ir.mrahimy.ingress.portal.view

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ir.map.sdk_services.models.MaptexError
import ir.map.sdk_services.models.base.ResponseListener
import ir.mrahimy.ingress.portal.R
import timber.log.Timber
import ir.map.sdk_services.ServiceHelper
import kotlinx.android.synthetic.main.activity_static_image.*
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration




class StaticImageActivity : AppCompatActivity(), ResponseListener<Bitmap> {
    override fun onSuccess(response: Bitmap?) {
        static_image.setImageBitmap(response)
    }

    fun getStaticBitmap() {
        ServiceHelper().getStaticMap(35.732527, 51.422448, 12, this)
    }

    override fun onError(error: MaptexError?) {
        Timber.d(error?.message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_static_image)
        intent.getParcelableArrayExtra("portal_locations").forEach {
            Timber.d("$it")
        }
        if (!ImageLoader.getInstance().isInited) {
            // Create global configuration and initialize ImageLoader with this config
            val config = ImageLoaderConfiguration.Builder(this).build()
            ImageLoader.getInstance().init(config)
        }
        getStaticBitmap()
    }
}

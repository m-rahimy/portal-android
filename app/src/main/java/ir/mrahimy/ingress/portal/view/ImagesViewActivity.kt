package ir.mrahimy.ingress.portal.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ir.mrahimy.ingress.portal.R
import timber.log.Timber

class ImagesViewActivity : AppCompatActivity() {

    companion object {
        val TAG = ImagesViewActivity ::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_view)

        val images = intent?.extras?.getParcelableArray("portal_images")
        images?.forEach {
            Timber.d("$TAG, $it")
        }

    }
}

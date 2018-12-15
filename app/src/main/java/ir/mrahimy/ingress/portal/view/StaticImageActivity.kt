package ir.mrahimy.ingress.portal.view

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ir.mrahimy.ingress.portal.R
import kotlinx.android.synthetic.main.activity_static_image.*
import ir.mrahimy.ingress.portal.model.ParcelablePortalJuncLocation

class StaticImageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_static_image)
        val pjl = intent.getParcelableArrayExtra("portal_locations")[0] as ParcelablePortalJuncLocation

        pjl.lat = 35.839446; pjl.lon = 50.971462
    }
}

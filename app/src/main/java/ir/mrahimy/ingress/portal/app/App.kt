package ir.mrahimy.ingress.portal.app

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import ir.map.sdk_map.MapSDK
import ir.mrahimy.ingress.portal.BuildConfig
import timber.log.Timber

/**
 * Created by vincent on 11/16/18.
 */

class App : Application(){
    //TODO: request sync after every portal insert

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        if (!ImageLoader.getInstance().isInited) {
            // Create global configuration and initialize ImageLoader with this config
            val config = ImageLoaderConfiguration.Builder(this).build()
            ImageLoader.getInstance().init(config)
        }
        MapSDK.init(this)

    }
}

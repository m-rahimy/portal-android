package ir.mrahimy.ingress.portal.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
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

        Fresco.initialize(this)
        MapSDK.init(this)

    }
}

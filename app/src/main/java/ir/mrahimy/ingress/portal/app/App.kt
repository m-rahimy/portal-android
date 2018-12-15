package ir.mrahimy.ingress.portal.app

import android.app.Application
import ir.mrahimy.ingress.portal.BuildConfig

/**
 * Created by vincent on 11/16/18.
 */

class App : Application() {
    //TODO: request sync after every portal insert

    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }

    }
}

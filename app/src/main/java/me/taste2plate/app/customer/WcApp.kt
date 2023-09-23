package me.taste2plate.app.customer

//import dev.anvith.blackbox.Blackbox
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.appsflyer.AppsFlyerLib
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import com.facebook.appevents.AppEventsLogger
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.libraries.places.api.Places
import com.google.firebase.analytics.FirebaseAnalytics
import com.trackier.sdk.TrackierSDK
import com.trackier.sdk.TrackierSDKConfig
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import me.taste2plate.app.customer.di.DaggerAppComponent
import me.taste2plate.app.customer.utils.AppConfig
import me.taste2plate.app.customer.utils.AppUtils
import java.util.Objects


class WcApp : DaggerApplication() {
    init {
        instance = this
    }

    companion object {
        private var instance: WcApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        ActivityLifecycleCallback.register(this)
        super.onCreate()
        // for debug only
        val mFirebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserProperty(
            "ct_objectId",
            Objects.requireNonNull(CleverTapAPI.getDefaultInstance(this))?.cleverTapID
        );
        // Blackbox.context(this).init()
        Fresco.initialize(this);
       // FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(this);
        Places.initialize(applicationContext, "AIzaSyCO5CDU2-xVi6VRy14HhptZ3A8Bztx5Ps4")
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/GT-America-Regular.otf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //trackier
        trackier()

        //appflyter
        setupAppFlyter()

    }

    private fun setupAppFlyter() {
        Log.e("setupAppFlyter", "AppFlyter setup")
        val devKey = "sRXbgREMUcXQ6VbHhb2CCb"
        val appFlyerLib = AppsFlyerLib.getInstance()

        appFlyerLib.setDebugLog(true)
        appFlyerLib.setMinTimeBetweenSessions(0)

        appFlyerLib.init(devKey, null, this)
        appFlyerLib.start(this)
    }

    private fun trackier() {
        val TR_DEV_KEY = "3dafc9f1-61af-4cc5-aff4-b09d98fed328"

        val sdkConfig = TrackierSDKConfig(this, TR_DEV_KEY, "testing")

        //To defer sdk start
        sdkConfig.setManualMode(true)
        TrackierSDK.setLocalRefTrack(true, "_")
        TrackierSDK.initialize(sdkConfig)

        //explicitly fire Install
        TrackierSDK.fireInstall()
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.create()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        try {
            AppConfig.onConfigChanged(applicationContext, newConfig)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onConfigurationChanged(newConfig)
    }

}

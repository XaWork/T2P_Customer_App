package me.taste2plate.app.customer

//import dev.anvith.blackbox.Blackbox
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsConstants
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
import java.util.*


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
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Places.initialize(applicationContext, "AIzaSyAe_TB4tSHMVBT2weqw74MR5-EHZZ4HBnc")
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

    }

    private fun trackier() {
        val TR_DEV_KEY = "3dafc9f1-61af-4cc5-aff4-b09d98fed328"

        val sdkConfig = TrackierSDKConfig(this, TR_DEV_KEY, "testing")

        //To defer sdk start
        sdkConfig.setManualMode(true)
        TrackierSDK.setLocalRefTrack(true, "_")
        TrackierSDK.initialize(sdkConfig)

        //TrackierSDK.initialize(sdkConfig)
        //Assosiate User Info during initialization of sdk
        //val appUtils = AppUtils(context)
        //TrackierSDK.setUserId("Xa kaler")
        //TrackierSDK.setUserEmail("xakaler@gmail.com")
        //TrackierSDK.initialize(sdkConfig)


        //explicitly fire Install
        TrackierSDK.fireInstall()

        /*//Disable Organic Track
        sdkConfig.disableOrganicTracking(true)
        TrackierSDK.initialize(sdkConfig)*/
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

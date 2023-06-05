package me.taste2plate.app.customer.di

import dagger.Module
import dagger.Provides
import me.taste2plate.app.customer.Config
import me.taste2plate.app.customer.WcApp
import me.taste2plate.app.Woocommerce

import javax.inject.Singleton

@Module
class AppModule {

    internal var app: WcApp? = null

    internal fun AppModule(application: WcApp) {
        app = application
    }

    @Provides
    @Singleton
    internal fun providesApplication(): WcApp {
        return app!!
    }

    @Provides
    @Singleton
    internal fun providesWoocommerce(): Woocommerce {

        return Woocommerce.Builder()
            .setSiteUrl(Config.siteUrl)
            .setApiVersion(Woocommerce.API_V3)
            .setConsumerKey(Config.consumerKey)
            .setConsumerSecret(Config.consumerSecret)
            .build()
    }

}

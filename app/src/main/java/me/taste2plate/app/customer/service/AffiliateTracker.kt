package me.taste2plate.app.customer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.taste2plate.app.customer.utils.AppUtils

class InstallReferrerReceiver  : BroadcastReceiver() {
    private var referrer: String? = ""
/*
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == "com.android.vending.INSTALL_REFERRER") {
            val extras = intent.extras

            val appUtils = AppUtils(context)
            appUtils.saveAffiliate(extras.toString())

            if (extras != null) {
                referrer =
                    "extras is : $extras\n----------------\naffiliated id is : " + extras.getString(
                        "affiliate_id"
                    ) + "\n--------\n" + "affiliated id is : " + extras.getString(
                        "token"
                    )
                appUtils.saveAffiliate(referrer)
            }
            Log.e("REFERRER", "Referer is: $referrer")
        }
    }*/



    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action != null && intent.action == "com.android.vending.INSTALL_REFERRER") {
            val referrer = intent.getStringExtra("referrer")
            referrer?.let { extractReferrerData(it, context!!) }
        }
    }

    private fun extractReferrerData(referrer: String, context: Context) {
        val params = referrer.split("&")
        var affiliateId: String? = null
        var token: String? = null
        for (param in params) {
            if (param.startsWith("affiliate_id=")) {
                affiliateId = param.substringAfter("affiliate_id=")
            } else if (param.startsWith("token=")) {
                token = param.substringAfter("token=")
            }
        }

        saveUserIdAndToken(affiliateId, token, context)
    }

    private fun saveUserIdAndToken(userId: String?, token: String?, context: Context) {
        val appUtils = AppUtils(context)
        appUtils.isInstallFromPlayStore = false
        appUtils.saveReferralInfo(userId, token)
    }
}
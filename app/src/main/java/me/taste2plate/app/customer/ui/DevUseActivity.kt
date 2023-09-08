package me.taste2plate.app.customer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import kotlinx.android.synthetic.main.activity_dev_use.referrerTV
import kotlinx.android.synthetic.main.activity_main.txt
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.utils.AppUtils

class DevUseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_use)


        val referrerClient: InstallReferrerClient = InstallReferrerClient.newBuilder(this).build()
        referrerClient.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                // This method is called when install referrer setup is finished.
                when (responseCode) {
                    // We are using when (similar to switch case) to check the response.
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        // This case is called when the status is OK.
                        var response: ReferrerDetails? = null
                        try {
                            // On below line we are getting referrer details
                            // by calling getInstallReferrer.
                            response = referrerClient.installReferrer

                            // On below line we are getting referrer url.
                            val referrerUrl = response.installReferrer

                            // On below line we are getting referrer click time.
                            val referrerClickTime = response.referrerClickTimestampSeconds

                            // On below line we are getting app install time.
                            val appInstallTime = response.installBeginTimestampSeconds

                            // On below line we are getting our time when
                            // the user has used our app's instant experience.
                            val instantExperienceLaunched = response.googlePlayInstantParam

                            // On below line we are getting our
                            // app's install referrer.
                            val referrer = response.installReferrer

                            /* // On below line we are setting all details to our text view.
                             val isInstalledFromPlayStore = checkInstallationSource()
                             val link = intent.data?.toString()
                             if (!isInstalledFromPlayStore) {
                                 Log.e(
                                     "splash",
                                     "install from play store $isInstalledFromPlayStore \nlink is $link"
                                 )
                                 if (link != null && link.contains("affiliate_id=") && link.contains(
                                         "token="
                                     )
                                 ) {
                                     val userId = link.substringAfter("id=").substringBefore("&")
                                     val token = link.substringAfter("token=")
                                     Log.e("splash", "User id : $userId\ntoken : $token")
                                 }
                             }

                             //--------------------------------

                             val appUtils = AppUtils(this@DevUseActivity)
                             val affDate = appUtils.affiliate*/

                            //--------------------------------


                            val txt = "Referrer is :\n$referrerUrl\n" +
                                    "Referrer Click Time is : $referrerClickTime\n" +
                                    "App Install Time: $appInstallTime\n" +
                                    "instantExperienceLaunched : $instantExperienceLaunched\n" +
                                    "referrer : $referrer\n\n-----------------"

                            referrerTV.text = txt
                            Log.e("Dev", "Referral url is : $txt")
                            /* "\n\nRefferal link is : $link\n\n-----------------" +
                             "\n\nBundle is : $affDate"*/
                        } catch (e: RemoteException) {
                            // Handling error casex`.
                            e.printStackTrace()
                        }
                    }

                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                        // API not available on the current Play Store app.
                        Toast.makeText(
                            this@DevUseActivity,
                            "Feature not supported..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                        // Connection couldn't be established.
                        Toast.makeText(
                            this@DevUseActivity,
                            "Fail to establish connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                Toast.makeText(this@DevUseActivity, "Service disconnected..", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        //referrerClient.endConnection()
    }

    private fun checkInstallationSource(): Boolean {
        val validInstallers = listOf("com.android.vending", "com.google.android.feedback")

        val installerPackageName = packageManager.getInstallerPackageName(this.packageName)
        Log.e("splash", "install package name $installerPackageName")
        // If the installerPackageName is null or empty, it means the app was not installed from the Play Store
        return installerPackageName != null && validInstallers.contains(installerPackageName)
    }
}
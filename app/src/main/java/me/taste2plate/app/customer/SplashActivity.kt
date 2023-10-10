package me.taste2plate.app.customer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.android.installreferrer.api.InstallReferrerClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.messaging.FirebaseMessaging
import com.trackier.sdk.TrackierEvent
import com.trackier.sdk.TrackierSDK
import me.taste2plate.app.customer.common.Status
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity
import me.taste2plate.app.customer.utils.AppUtils
import me.taste2plate.app.customer.viewmodels.UserViewModel
import me.taste2plate.app.data.api.AnalyticsAPI
import me.taste2plate.app.data.api.ApiService
import me.taste2plate.app.data.api.IpAddressResponse
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.data.api.RegistrationData
import me.taste2plate.app.models.LogCreatedResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.math.min
import kotlin.system.exitProcess


class SplashActivity : WooDroidActivity<UserViewModel>() {

    private var customer: RegistrationData? = null
    override lateinit var viewModel: UserViewModel
    private var isVersionCheckComplete: Boolean = false
    private var onResumeCalled = false
    private var permissionDoNotAllowed = false
    private var notificationPermissionDoNotAllowed = false
    private lateinit var referrerClient: InstallReferrerClient


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        viewModel = getViewModel(UserViewModel::class.java)
        customer = AppUtils(this).user
        isVersionCheckComplete = false

    }
    /*
        private fun getDynamicLink() {
            referrerClient = InstallReferrerClient.newBuilder(this).build()
            referrerClient.startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(responseCode: Int) {
                    if (responseCode == InstallReferrerClient.InstallReferrerResponse.OK) {
                        try {
                            val response: ReferrerDetails = referrerClient.installReferrer
                            val referrerUrl: String = response.installReferrer

                            extractReferrerData(referrerUrl)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onInstallReferrerServiceDisconnected() {
                    // Handle disconnect
                }
            })
        }

        private fun extractReferrerData(referrerUrl: String) {
            // Parse the referrer string to extract affiliateId and token
            val params = referrerUrl.split("&")
            var affiliateId: String? = null
            var token: String? = null
            for (param in params) {
                if (param.startsWith("affiliate_id=")) {
                    affiliateId = param.substringAfter("affiliate_id=")
                } else if (param.startsWith("token=")) {
                    token = param.substringAfter("token=")
                }
            }
            Log.e("Splash", "Affiliate id : $affiliateId\ntoken : $token")
        }

        private fun saveUserIdAndToken(userId: String?, token: String?) {
            val appUtils = AppUtils(this)
            appUtils.isInstallFromPlayStore = false
            appUtils.saveReferralInfo(userId, token)

        }*/

    override fun onDestroy() {
        super.onDestroy()
        if (::referrerClient.isInitialized) {
            referrerClient.endConnection()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkAndSaveInstallInfo() {
        val isInstalledFromPlayStore = checkInstallationSource()
        if (!isInstalledFromPlayStore) {
            val link = intent.data?.toString()
            Log.e("splash", "install from play store $isInstalledFromPlayStore \nlink is $link")
            if (link != null && link.contains("affiliate_id=") && link.contains("token=")) {
                val userId = link.substringAfter("id=").substringBefore("&")
                val token = link.substringAfter("token=")
                Log.e("splash", "User id : $userId\ntoken : $token")
                saveUserIdAndToken(userId, token)
            }
        }

        if (checkPermissions()) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU)
                checkNotificationPermission()
            else
                apiCalls()
        }
    }

    private fun checkInstallationSource(): Boolean {
        // A list with valid installers package name
        // A list with valid installers package name
        val validInstallers = listOf("com.android.vending", "com.google.android.feedback")

        val installerPackageName = packageManager.getInstallerPackageName(this.packageName)
        Log.e("splash", "install package name $installerPackageName")
        // If the installerPackageName is null or empty, it means the app was not installed from the Play Store
        return installerPackageName != null && validInstallers.contains(installerPackageName)
    }

    private fun saveUserIdAndToken(userId: String, token: String) {
        val appUtils = AppUtils(this)
        appUtils.isInstallFromPlayStore = false
        appUtils.saveReferralInfo(userId, token)
    }

   /* private fun startTrackingWithTrackier() {
        val event = TrackierEvent(TrackierEvent.START_TRIAL)
        TrackierSDK.trackEvent(event)

    }*/

    private fun pushNotification() {
        try {

            FirebaseMessaging.getInstance().subscribeToTopic("install")

            /*   FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
                   if (!TextUtils.isEmpty(token)) {
                       Log.e(
                           "Token",
                           "retrieve token successful : $token"
                       )
                   } else {
                       Log.e(
                           "Token",
                           "token should not be null..."
                       )
                   }
               }.addOnFailureListener { }
                   .addOnCanceledListener {}
                   .addOnCompleteListener { task: Task<String> ->
                       try {
                           Log.e(
                               "Token",
                               "This is the token : " + task.result
                           )
                       } catch (exception: Exception) {

                       }
                   }*/
        } catch (exception: IOException) {
            Log.e("firebase", exception.toString())
        }

    }

    private fun addLog(request: LogRequest) {
        viewModel.addLog(request).observe(this) { response ->
            when (response!!.status()) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    if (!AppUtils(this).isInstallFromPlayStore)
                        install(response.data())
                }

                Status.ERROR -> {}
                Status.EMPTY -> {}
            }
        }
    }

    private fun install(logResponse: LogCreatedResponse) {
        viewModel.install(
            AppUtils(this).referralInfo[1],
            logResponse.result._id,
            "fc36e1f99d0cfdee7d34",
            AppUtils(this).token,
            logResponse.result.createdAt,
        ).observe(this) { response ->
            when (response!!.status()) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    getAppData()
                }

                Status.ERROR -> {}
                Status.EMPTY -> {}
            }
        }
    }

    private fun getAppData() {
        viewModel.fetchAppData().observe(this, Observer { response ->
            when (response!!.status()) {
                Status.LOADING -> {
                    showLoading()
                }

                Status.SUCCESS -> {
                    AppUtils(this).saveAppData(response.data())
                    stopShowingLoading()
                    val minLength = min(
                        BuildConfig.VERSION_NAME.length,
                        response.data().result.customerAndroidVersion.length
                    )

                    val replace1 =
                        BuildConfig.VERSION_NAME.take(minLength).replace(".", "").toFloat()
                    val replace2 = response.data().result.customerAndroidVersion.take(minLength)
                        .replace(".", "").toFloat()

                    Log.e(
                        "TAG",
                        "minLength: $minLength\n replace1 : $replace1\n replace2: $replace2"
                    )

                    if (BuildConfig.VERSION_NAME.take(minLength).replace(".", "")
                            .toFloat() < response.data().result.customerAndroidVersion.take(
                            minLength
                        ).replace(".", "").toFloat()
                    ) {
                        showVersionAlert()
                    } else {
                        moveToNext()
                    }
                }

                Status.ERROR -> {
                    stopShowingLoading()
                    MaterialAlertDialogBuilder(this@SplashActivity)
                        .setTitle("No Internet")
                        .setCancelable(false)
                        .setMessage("No Internet Connection!")
                        .setPositiveButton("Exit") { _, _ ->
                            exitProcess(0)
                        }
                        .show()
                }

                Status.EMPTY -> {
                    stopShowingLoading()
                }
            }
        })
    }

    private fun moveToNext() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (customer != null) {
                startActivity(Intent(baseContext, HomeActivity::class.java))
            } else {
                startActivity(Intent(baseContext, OnBoardActivity::class.java))
            }
            finish()
        }, 2000)
    }

    private fun showVersionAlert() {
        AlertDialog.Builder(this).setCancelable(false)
            .setMessage(getString(R.string.imp_update_msg))
            .setTitle("Alert")
            .setCancelable(false)
            .setPositiveButton(getString(R.string.alert_ok)) { _, _ ->
                val appPackageName = packageName // getPackageName() from Context or Activity object
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$appPackageName")
                        )
                    )
                } catch (anfe: android.content.ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                    )
                }
            }.setNegativeButton(getString(R.string.alert_cancel)) { _, _ -> exitProcess(0) }
            .create().show()
    }

    private fun apiCalls() {

        //startTracking()

        getIpAddress()
        //startTrackingWithTrackier()

        // pushNotification()
    }


    private fun getIpAddress() {
        val context = this
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.ipify.org")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var ip = ""


        val apiService = retrofit.create(ApiService::class.java)

        val call = apiService.getIpAddress()
        call.enqueue(object : retrofit2.Callback<IpAddressResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<IpAddressResponse>,
                response: retrofit2.Response<IpAddressResponse>
            ) {
                if (response.isSuccessful) {
                    ip = response.body()?.ip ?: ""
                    Log.e("IP", "Success : $response")
                    AppUtils(context).saveIpAddress(ip)
                    //send event info
                    val analytics = AnalyticsAPI()
                    val appUtils = AppUtils(context)
                    val logRequest = LogRequest(
                        category = appUtils.referralInfo[0],
                        token = appUtils.referralInfo[1],
                        type = "splash",
                        event = "Enter in splash screen",
                        page_name = "/splash",
                        source = "android",
                        user_id = if (appUtils.user != null) appUtils.user.id else "",
                        geo_ip = AppUtils(context).ipAddress,
                        product_id = ""
                    )

                    addLog(logRequest)
                } else {
                    Log.e("Analytics", "failed : $response")
                }
            }

            override fun onFailure(call: Call<IpAddressResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission() {

        val listPermissionsNeeded: ArrayList<String> = ArrayList()
        val notificationPermission =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            )

        if (notificationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.POST_NOTIFICATIONS);
        }


        if (listPermissionsNeeded.isNotEmpty()) {
            PermissionUtils.requestPermission(
                this@SplashActivity, listPermissionsNeeded,
                0
            )
        } else {
            apiCalls()
        }

    }

    private fun checkPermissions(): Boolean {
        val permissionLocation1 =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )

        val permissionLocation2 =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )


        val listPermissionsNeeded: ArrayList<String> = ArrayList()

        if (permissionLocation1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionLocation2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }



        if (listPermissionsNeeded.isNotEmpty()) {
            PermissionUtils.requestPermission(
                this@SplashActivity, listPermissionsNeeded,
                1
            )
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {

            val allPermissionsGranted =
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    true
                } else if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                ) {
                    false
                } else {
                    false
                }

            /* for (result in grantResults) {
                 if (result != PackageManager.PERMISSION_GRANTED) {
                     allPermissionsGranted = false
                     break
                 }
             }*/

            if (allPermissionsGranted) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU)
                    checkNotificationPermission()
                else
                    apiCalls()
            } else {
                MaterialAlertDialogBuilder(this@SplashActivity)
                    .setTitle("Alert")
                    .setCancelable(false)
                    .setMessage("Location services are disabled")
                    .setPositiveButton("Allow") { _, _ ->
                        openAppSettings()
                    }
                    .setNegativeButton(getString(R.string.alert_cancel)) { _, _ ->
                        exitProcess(
                            0
                        )
                    }
                    .show()
            }
        }
        else if (requestCode == 0) {
            apiCalls()
        }

        /*  val permissionReturn: Int = ContextCompat.checkSelfPermission(
              this@SplashActivity,
              android.Manifest.permission.ACCESS_FINE_LOCATION
          )
          val permissionReturn1: Int = ContextCompat.checkSelfPermission(
              this@SplashActivity,
              android.Manifest.permission.ACCESS_COARSE_LOCATION
          )
          val permissionReturn2: Int = ContextCompat.checkSelfPermission(
              this@SplashActivity,
              android.Manifest.permission.POST_NOTIFICATIONS
          )

          Log.e(
              "location",
              "Access fine location: $permissionReturn\n" +
                      "Access Coarse Location : $permissionReturn1\n" +
                      "Post Notification : $permissionReturn2\n" +
                      "request code : $requestCode\n" +
                      "permissions : $permissions\n" +
                      "grantResults : ${grantResults.size}"
          )

          when (requestCode) {
              0 -> {
                  if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                      if (ContextCompat.checkSelfPermission(
                              this@SplashActivity,
                              android.Manifest.permission.POST_NOTIFICATIONS
                          ) == PackageManager.PERMISSION_GRANTED
                      ) {
                          apiCalls()
                          Toast.makeText(this, "Notification Permission Granted", Toast.LENGTH_SHORT)
                              .show()
                      }
                  } else {
                      if (!notificationPermissionDoNotAllowed) {
                          if (ActivityCompat.shouldShowRequestPermissionRationale(
                                  this@SplashActivity,
                                  android.Manifest.permission.POST_NOTIFICATIONS
                              )
                          ) {
                              checkNotificationPermission()
                          } else {
                              notificationPermissionDoNotAllowed = true
                              *//*MaterialAlertDialogBuilder(this@SplashActivity)
                                .setTitle("Notification Disabled")
                                .setCancelable(false)
                                .setMessage("Notification service is disabled")
                                .setPositiveButton("Start") { _, _ ->
                                    startActivity(Intent(android.provider.Settings.ACTION_ALL_APPS_NOTIFICATION_SETTINGS))
                                }
                                .setNegativeButton(getString(R.string.alert_cancel)) { _, _ ->
                                    exitProcess(
                                        0
                                    )
                                }
                                .show()*//*
                            apiCalls()
                            //Toast.makeText(this, "Notification Permission Denied", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    if (ContextCompat.checkSelfPermission(
                            this@SplashActivity,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(
                            this@SplashActivity,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED

                    ) {
                        checkNotificationPermission()

                        Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    if (!permissionDoNotAllowed) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this@SplashActivity,
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                                this@SplashActivity,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        ) {
                            checkPermissions()
                        } else {
                            permissionDoNotAllowed = true
                            MaterialAlertDialogBuilder(this@SplashActivity)
                                .setTitle("Location Disabled")
                                .setCancelable(false)
                                .setMessage("Location services are disabled")
                                .setPositiveButton("Start") { _, _ ->
                                    startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                                }
                                .setNegativeButton(getString(R.string.alert_cancel)) { _, _ ->
                                    exitProcess(
                                        0
                                    )
                                }
                                .show()
                        }
                    }

                }
            }
        }*/
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if (onResumeCalled) {
            val hasLocationPermission = PackageManager.PERMISSION_GRANTED ==
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) && PackageManager.PERMISSION_GRANTED ==
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )

            if (hasLocationPermission) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU)
                    checkNotificationPermission()
                else
                    apiCalls()
            } else {
                Toast.makeText(this, "Please allow permission to move forward", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            if (checkPermissions()) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU)
                    checkNotificationPermission()
                else
                    apiCalls()
            }
            onResumeCalled = true
        }
    }

}

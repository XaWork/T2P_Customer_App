package me.taste2plate.app.customer

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.clevertap.android.sdk.CleverTapAPI
import com.clickzin.tracking.ClickzinTracker
import com.google.android.gms.tasks.Task
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
import me.taste2plate.app.data.api.RegistrationData
import java.io.IOException
import kotlin.math.min
import kotlin.system.exitProcess


class SplashActivity : WooDroidActivity<UserViewModel>() {

    private var customer: RegistrationData? = null
    override lateinit var viewModel: UserViewModel
    private var isVersionCheckComplete: Boolean = false
    private var permissionDoNotAllowed = false
    private var notificationPermissionDoNotAllowed = false


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialises the CleverTap SDK.
        var cleverTapDefaultInstance: CleverTapAPI? = CleverTapAPI.getDefaultInstance(applicationContext)

        setContentView(R.layout.activity_main)
        viewModel = getViewModel(UserViewModel::class.java)
        customer = AppUtils(this).user
        isVersionCheckComplete = false


        CleverTapAPI.getDefaultInstance(this)?.recordScreen("Splash")

        if (checkPermissions()) {
            if (checkNotificationPermission())
                apiCalls()
        }
        // Log.e("Token", "onCreate: ${FirebaseInstanceId.getInstance().getToken()}")
    }

    private fun startTrackingWithTrackier() {

        val event = TrackierEvent(TrackierEvent.START_TRIAL)
        TrackierSDK.trackEvent(event)

    }

    private fun startTracking() {
        val appKey = "kalpssoft"
        ClickzinTracker.getInstance().init(applicationContext, appKey, null)
        ClickzinTracker.getInstance().setCustomerId("pradeep")
        ClickzinTracker.getInstance().startTracking("Install")

        /*    val call = TrackerService().getBaseUrl().create(TrackerApi::class.java)

            call.install("1").enqueue(object : Callback<TrackerModel> {
                override fun onResponse(
                    call: Call<TrackerModel>,
                    response: Response<TrackerModel>
                ) {
                    Log.e("TAG", "onSuccess: ${response.body()}")
                }

                override fun onFailure(call: Call<TrackerModel>, t: Throwable) {
                    Log.e("TAG", "onFailure: $t")
                }
            })*/
    }

    private fun pushNotification() {
        try {

            FirebaseMessaging.getInstance().subscribeToTopic("install")

            FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
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
            }.addOnFailureListener { }.addOnCanceledListener {}
                .addOnCompleteListener { task: Task<String> ->
                    try {
                        Log.e(
                            "Token",
                            "This is the token : " + task.result
                        )
                    } catch (exception: Exception) {

                    }
                }
        } catch (exception: IOException) {
            Log.e("firebase", exception.toString())
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
        getAppData()

        startTracking()

        startTrackingWithTrackier()

        pushNotification()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission(): Boolean {
        val notificationPermission =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            )

        val listPermissionsNeeded: ArrayList<String> = ArrayList()

        if (notificationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.POST_NOTIFICATIONS);
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            PermissionUtils.requestPermission(
                this@SplashActivity, listPermissionsNeeded,
                0
            )
            return false;
        }
        return true;
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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

        val notificationPermission =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            )

        val listPermissionsNeeded: ArrayList<String> = ArrayList()

        if (permissionLocation1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionLocation2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        /*if (notificationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.POST_NOTIFICATIONS);
        }*/

        if (listPermissionsNeeded.isNotEmpty()) {
            PermissionUtils.requestPermission(
                this@SplashActivity, listPermissionsNeeded,
                1
            )
            return false;
        }
        return true;
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val permissionReturn: Int = ContextCompat.checkSelfPermission(
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
                        Toast.makeText(this, "Notification Permission Granted", Toast.LENGTH_SHORT).show()
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
                            /*MaterialAlertDialogBuilder(this@SplashActivity)
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
                                .show()*/
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

                        Toast.makeText(this, "Location Permission Granted", Toast.LENGTH_SHORT).show()
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
        }
    }

    override fun onResume() {
        super.onResume()

    }
}

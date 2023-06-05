package me.taste2plate.app.customer.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import me.taste2plate.app.customer.BuildConfig
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.ui.home.HomeActivity
import me.taste2plate.app.customer.utils.AppUtils

const val channelId = "channelId"
const val channelName = "channelName"

class FirebaseInstanceService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.e("Token", "token in firebase instance service class: $token")
        AppUtils(applicationContext).saveToken(token)
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences("StarterApp", MODE_PRIVATE).getString("token", "empty")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            Log.e("notification message", "onMessageReceived: ${message.notification!!.title!!}")
            addNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getFirebaseMessage(title: String, desc: String, img: Uri?) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        //PendingIntent.FLAG_UPDATE_CURRENT
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo_new)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, desc, img))

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())

    }

    private fun addNotification(title: String, desc: String) {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.t2p_logo)
            .setContentTitle(title)
            .setContentText(desc)
            .setAutoCancel(true)

        val notificationIntent = Intent(
            this,
            HomeActivity::class.java
        )
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        /*val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )*/

        builder.setContentIntent(pendingIntent)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())
    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, desc: String, img: Uri?): RemoteViews {
        val remoteViews = RemoteViews("me.taste2plate.app.customer", R.layout.lyt_notification)

        remoteViews.setTextViewText(R.id.notificationTitle, title)
        remoteViews.setTextViewText(R.id.notificationMessage, desc)
        remoteViews.setImageViewResource(R.id.notificationImage, R.drawable.logo_new)
        remoteViews.setImageViewUri(R.id.notificationImage1, img)


        return remoteViews
    }
}
package ru.sumin.servicestest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationManager =
                getSystemService(context, NotificationManager::class.java) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNLE_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Title")
                .setContentText("text")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build()
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNLE_NAME = "channel_name"
        private const val NOTIFICATION_ID = 1

        fun newInstance(context: Context): Intent {
            return Intent(context, AlarmReceiver::class.java)
        }
    }
}
package ru.sumin.servicestest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyForegroundService : Service(){
    private val scope = CoroutineScope(Dispatchers.Main)
    private var id = 0
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())

    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNLE_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Title")
        .setContentText("text")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .build()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Timer $i")
            }
        }
        return START_STICKY


    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()

    }

    private fun log(message: String) {
        Log.d("Services", "MyForegroundService $message")
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNLE_NAME = "channel_name"
        private const val NOTIFICATION_ID = 1


        fun newInstance(context: Context): Intent {
            return Intent(context, MyForegroundService::class.java)
        }
    }

}
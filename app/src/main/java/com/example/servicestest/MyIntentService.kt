package com.example.servicestest

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*

class MyIntentService : IntentService(SERVICE_NAME) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onHandleIntent(p0: Intent?) {
        log("Service onHandleIntent")
        for (i in 0 until 10) {
            Thread.sleep(1000)
            log("TIMER $i")
        }
    }


    override fun onCreate() {
        super.onCreate()
        setIntentRedelivery(false)
        log("Service onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("Service onDestroy")
    }

    private fun log(message: String) {
        Log.d("FOREGROUND_SERVICE_TAG", message)
    }

    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Title")
            .setContentText("Text")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }

    companion object {
        private const val SERVICE_NAME = "my_intent_service"
        private const val CHANNEL_ID = "service_start"
        private const val CHANNEL_NAME = "service_name"
        private const val NOTIFICATION_ID = 1
        fun newIntent(context: Context): Intent {

            return Intent(context, MyIntentService::class.java)
        }
    }
}
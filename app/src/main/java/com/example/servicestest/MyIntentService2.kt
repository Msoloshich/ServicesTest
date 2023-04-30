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

class MyIntentService2 : IntentService(SERVICE_NAME) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onHandleIntent(intent: Intent?) {
        log("Service onHandleIntent")
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        for (i in 0 until 10) {
            Thread.sleep(1000)
            log("TIMER $i $page")
        }
    }


    override fun onCreate() {
        super.onCreate()
        setIntentRedelivery(false)
        log("Service onCreate")
        setIntentRedelivery(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("Service onDestroy")
    }

    private fun log(message: String) {
        Log.d("FOREGROUND_SERVICE_TAG", message)
    }

    companion object {
        private const val SERVICE_NAME = "my_intent_service"
        private const val PAGE = "page"
        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyIntentService2::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}
package com.example.servicestest

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MyJobIntentService : JobIntentService() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onHandleWork(intent: Intent) {
        log("Service onHandleIntent")
        val page = intent?.getIntExtra(PAGE, 0)
        for (i in 0 until 10) {
            Thread.sleep(1000)
            log("TIMER $i $page")
        }
    }

    override fun onCreate() {
        super.onCreate()
        log("Service onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("Service onDestroy")
    }

    private fun log(message: String) {
        Log.d("JO_INTENT_SERVICE_TAG", message)
    }

    companion object {
        private const val JOB_ID = 100
        private const val PAGE = "page"
        private fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }

        fun enqueue(context: Context, page: Int) {
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, newIntent(context, page))
        }
    }
}
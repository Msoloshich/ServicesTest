package com.example.servicestest

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onStartJob(params: JobParameters?): Boolean {
        log("JobService onStartJob")
        coroutineScope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("TIMER $i")
            }
            jobFinished(params, true)
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onCreate() {
        super.onCreate()
        log("JobService onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("JobService onDestroy")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", message)
    }

    companion object {
        const val JOB_ID = 1
    }

}
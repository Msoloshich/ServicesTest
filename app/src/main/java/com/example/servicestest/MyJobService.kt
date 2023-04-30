package com.example.servicestest

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log
import kotlinx.coroutines.*

class MyJobService : JobService() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onStartJob(params: JobParameters?): Boolean {
        log("JobService onStartJob")
        coroutineScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var workLaunch = params?.dequeueWork()

                while (workLaunch != null) {
                    val page = workLaunch.intent.getIntExtra(PAGE, 0)
                    for (i in 0 until 5) {
                        delay(1000)
                        log("TIMER $i $page")
                    }
                    params?.completeWork(workLaunch)
                    workLaunch = params?.dequeueWork()
                }
            }
            jobFinished(params, false)
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
        const val PAGE = "page"

        fun newIntent(page: Int): Intent {
            return Intent().apply {
                putExtra(PAGE, page)
            }
        }
    }

}
package com.example.servicestest

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorker(context: Context, private val workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0 until 10) {
            Thread.sleep(1000)
            log("TIMER $i $page")
        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d("MyWORK", "MyWorker: $message")
    }

    companion object {
        const val PAGE = "page"
        const val WORK_NAME = "workName"

        fun makeRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>().apply {
                setInputData(makeData(page))
                    .setConstraints(makeConstrains())
            }.build()
        }

        private fun makeData(page: Int):Data {
            return Data.Builder().apply { putInt(PAGE, page) }.build()
        }

        private fun makeConstrains(): Constraints {
            return Constraints.Builder().setRequiresCharging(true).build()
        }

    }
}
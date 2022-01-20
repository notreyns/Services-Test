package ru.sumin.servicestest

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kotlinx.coroutines.*

class MyJobService: JobService() {
    private val scope= CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        scope.launch {
            for(i in 0 until 100){
                delay(1000)
                log("Timer $i")
            }
            jobFinished(params, true)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
        scope.cancel()

    }

    private fun log(message: String){
        Log.d("Services", "MyJobService $message")
    }
    companion object{
        const val JOB_ID = 111
    }


}
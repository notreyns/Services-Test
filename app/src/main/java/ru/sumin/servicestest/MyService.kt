package ru.sumin.servicestest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService: Service() {
    private val scope= CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            for(i in 0 until 100){
                delay(1000)
                log("Timer $i")
            }
        }


        return super.onStartCommand(intent, flags, startId)


    }
    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")

    }

    private fun log(message: String){
        Log.d("Services", "MyService $message")
    }
    companion object{
        fun newInstance(context: Context): Intent{
            return Intent(context, MyService::class.java)
        }
    }

}
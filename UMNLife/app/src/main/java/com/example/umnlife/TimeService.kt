package com.example.umnlife

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class TimeService: Service() {
    override fun onBind(p0: Intent?):IBinder? = null
    private val timer = Timer()

    override fun onStartCommand(intent: Intent?, flag: Int, startId: Int): Int {
        val time: Double = intent!!.getDoubleExtra(TIME_EXTRA, 0.0)
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
    }

    private inner class TimeTask(private var time: Double):TimerTask(){
        override fun run() {
            val intent = Intent(TIMER_UPDATE)
            time++
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)
        }
    }
    companion object{
        const val TIMER_UPDATE = "timeUpdated"
        const val TIME_EXTRA = "timeExtra"
    }

}

package com.example.umnlife


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.umnlife.databinding.ActivityInGameBinding
import kotlin.math.roundToInt

class ActivityInGame: AppCompatActivity() {
    private lateinit var binding: ActivityInGameBinding
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private var timerStarted = false
    private var hours = 0
    private var hours2 = 0
    private var minutes = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_in_game)

        binding = ActivityInGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getChar: Int = intent.getStringExtra("char")?.toInt() ?: R.drawable.char4

        val refChar = when(getChar){
            R.drawable.char1 -> R.drawable.char1
            R.drawable.char2 -> R.drawable.char2
            R.drawable.char3 -> R.drawable.char3
            else-> R.drawable.char4
        }
        binding.character.setImageResource(refChar)

        val geName: String? = intent.getStringExtra("name")
        binding.name.setText(geName)

        serviceIntent = Intent(applicationContext, TimeService::class.java)
        registerReceiver(updateTime, IntentFilter(TimeService.TIMER_UPDATE))
        startTimer()
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
    }

    private fun startTimer(){
        serviceIntent.putExtra(TimeService.TIME_EXTRA, time)
        startService(serviceIntent)
        timerStarted = true
    }

    private fun stopTimer(){
        stopService(serviceIntent)
        timerStarted = false
    }

    private val updateTime:BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent:Intent) {
            time = intent.getDoubleExtra(TimeService.TIME_EXTRA, 0.0)
            binding.jam.text = getTimeStringFromDouble(time)
            tampilWaktu()

        }
    }

    private fun tampilWaktu(){
        var Salam:String;
        var bg:Int;
        when(this.hours){
            in 1 until 10 -> {
                Salam ="Pagi"
                bg = R.drawable.bgpagi
            }
            in 10 until 14 -> {
                Salam = "Siang"
                bg = R.drawable.bgsiang
            }
            in 14 until 16 -> {
                Salam = "Sore"
                bg = R.drawable.bgsore
            }
            else -> {
                Salam = "Malam"
                bg = R.drawable.bgmalam
            }
        }
        if(this.hours != this.hours2){
            binding.bagroundInnerGame.setImageResource(bg)
            SalamToast(this).showCustomToast(Salam,this)
            this.hours2 = this.hours
        }

    }

    private fun getTimeStringFromDouble(time:Double): String {
        val resultInt = time.roundToInt()
        this.hours = 60*resultInt%86400/3600
        this.minutes = 60*resultInt%86400%3600/60
        val seconds = resultInt%86400%3600%60
        return String.format("%02d:%02d", hours, minutes)
    }

}
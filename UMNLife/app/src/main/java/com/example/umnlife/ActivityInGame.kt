package com.example.umnlife


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.example.umnlife.TimerService.Companion.resetTimeOut
import com.example.umnlife.TimerService.Companion.setTimeout
import com.example.umnlife.databinding.ActivityInGameBinding
import java.util.*

class ActivityInGame: AppCompatActivity() {
    private lateinit var binding: ActivityInGameBinding
    protected var handler = Handler(Looper.getMainLooper())
    private var time = 0
    private var hours = 0
    private val timer = Timer()
    private var progressMakan = 50
    private var progressTidur = 50
    private var progressMain = 50
    private var progressBljr = 0
    private var study = Handler()
    private val waktuDo = TimerService()
    private var semester = 1
    private var mMediaPlayer: MediaPlayer? = null
    private var timeDesc = "Selamat Malam";
    private var char:Int = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_in_game)

        binding = ActivityInGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val runnable = Runnable{
//            Toast.makeText(this, "Kamu di DO", Toast.LENGTH_LONG).show()
//        }


        defaultValue()
        TimerService.setInterval({
            runOnUiThread {
                time++
                val seconds= time%60
                val hours = time/60
                val nowHours:String = String.format("%02d:%02d",hours,seconds)
                binding.jam.setText(nowHours)
                tampilWaktu()
            }
        }, 1000)
        var flagMakan = false

        TimerService.setInterval({
            runOnUiThread{
                progressMakan-=2
                if(progressMakan <= 0){
//                    TimerService.resetInterval()
                }else if(flagMakan == true && progressMakan < 20){
                    flagMakan = false
                    Toast.makeText(this, "Kamu Lapar", Toast.LENGTH_LONG).show()
                }else{
                    flagMakan = true
                }
                binding.barMakan.setProgress(progressMakan)
            }
        },1000)

        var flagTidur = false
        TimerService.setInterval({
            runOnUiThread{
                progressTidur-=2
                binding.barTidur.setProgress(progressTidur)
                if(progressMakan <= 0){
//                    TimerService.resetInterval()
                }else if(flagTidur == true && progressTidur < 20){
                    flagTidur = false
                    Toast.makeText(this, "Kamu kurang tidur", Toast.LENGTH_LONG).show()

                }else{
                    flagTidur = true
                }
            }
        },10000)

        var flagMain = false
        TimerService.setInterval({
            runOnUiThread{
                progressMain-=2
                binding.barMain.setProgress(progressMain)
                if(progressMakan <= 0){
//                    TimerService.resetInterval()
                }else if(flagMain == true && progressMain < 20){
                    flagMain = false
                    Toast.makeText(this, "Kamu sangat sedih", Toast.LENGTH_LONG).show()
                }else{
                    flagMain = true
                }
            }
        },5000)

        waktuDo.setTimeout({Toast.makeText(this, "Kamu DI DO", Toast.LENGTH_LONG).show()},60000)

        binding.button2.setOnClickListener{
            onStudy(this.waktuDo)
        }
        binding.buttonMain.setOnClickListener{onMain()}
        binding.buttonMakan.setOnClickListener{onMakan()}
        binding.buttonTidur.setOnClickListener{onTidur()}

    }

    fun playSound(source: Int) {
        if (mMediaPlayer != null){
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }

        mMediaPlayer = MediaPlayer.create(this, source)
        mMediaPlayer!!.isLooping = true
        mMediaPlayer!!.start()
    }

    fun onMain(){
        progressMain+=10
        binding.barMain.setProgress(progressMain)
    }

    fun onMakan(){
        progressMakan+=20
        binding.barMakan.setProgress(progressMakan)
    }

    fun onTidur(){
        progressTidur+=30
        binding.barTidur.setProgress(progressTidur)
        binding.character.setImageResource(R.drawable.char1tidur)
        TimerService.setTimeout({binding.character.setImageResource(char)},3000)
    }
    fun onStudy(waktuDo : TimerService){
        progressBljr+=20
        binding.barBelajar.setProgress(progressBljr)
        waktuDo.resetTimeOut()
        waktuDo.setTimeout({Toast.makeText(this, "Kamu DI DO", Toast.LENGTH_LONG).show()},60000)
        if (progressBljr >= 100){
            progressBljr = 0
            semester++
            binding.semester.setText(String.format("Semester %d", semester))
        }
    }

    fun defaultValue(){
        val getChar: Int = intent.getStringExtra("char")?.toInt() ?: R.drawable.char4
        char = getChar
        val refChar = when(getChar){
            R.drawable.char1 -> R.drawable.char1
            R.drawable.char2 -> R.drawable.char2
            R.drawable.char3 -> R.drawable.char3
            else-> R.drawable.char4
        }
        binding.character.setImageResource(refChar)

        val geName: String? = intent.getStringExtra("name")
        binding.name.setText(geName)
        binding.bagroundInnerGame.setImageResource(R.drawable.bgmalam)
        SalamToast(this).showCustomToast("Selamat Malam",this)
        playSound(R.raw.musikmalam)
    }

    private fun tampilWaktu(){
        var Salam:String;
        var bg:Int;
        var sound:Int;
        when(time/60){
            in 1 until 10 -> {
                Salam ="Selamat Pagi"
                bg = R.drawable.bgpagi
                sound = R.raw.musikpagi
            }
            in 10 until 14 -> {
                Salam = "Selamat Siang"
                bg = R.drawable.bgsiang
                sound = R.raw.musiksiang
            }
            in 14 until 16 -> {
                Salam = "Selamat Sore"
                bg = R.drawable.bgsore
                sound = R.raw.musiksore
            }
            else -> {
                Salam = "Selamat Malam"
                bg = R.drawable.bgmalam
                sound = R.raw.musikmalam
            }
        }
        if(time/60 != this.hours){
            binding.bagroundInnerGame.setImageResource(bg)
            SalamToast(this).showCustomToast(Salam,this)
            this.hours = time/60

            if(timeDesc != Salam){
                playSound(sound)
                timeDesc = Salam
            }
        }
    }
}
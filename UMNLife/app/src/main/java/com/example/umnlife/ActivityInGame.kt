package com.example.umnlife


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.example.umnlife.TimerService.Companion.resetTimeOut
import com.example.umnlife.databinding.ActivityInGameBinding
import java.util.*

class ActivityInGame: AppCompatActivity() {
    private lateinit var binding: ActivityInGameBinding
    protected var handler = Handler(Looper.getMainLooper())
    private var time = 0
    private var hours = 0
    val timer = Timer()
    private var progressMakan = 50
    private var progressTidur = 50
    private var progressMain = 50
    private var progressBljr = 0
    private var study = Handler()
    private val waktuDo = TimerService()
    private var semester = 1


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

        waktuDo.setTimeout({Toast.makeText(this, "Kamu DI DO", Toast.LENGTH_LONG).show()},10000)

        binding.button2.setOnClickListener{
            onStudy(this.waktuDo)
        }
        binding.buttonMain.setOnClickListener{onMain()}
        binding.buttonMakan.setOnClickListener{onMakan()}
        binding.buttonTidur.setOnClickListener{onTidur()}

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
    }
    fun onStudy(waktuDo : TimerService){
        progressBljr+=20
        binding.barBelajar.setProgress(progressBljr)
        waktuDo.resetTimeOut()
        waktuDo.setTimeout({Toast.makeText(this, "Kamu DI DO", Toast.LENGTH_LONG).show()},10000)
        if (progressBljr >= 100){
            progressBljr = 0
            semester++
            binding.semester.setText(String.format("Semester %d", semester))
        }
    }

    fun defaultValue(){
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
        binding.bagroundInnerGame.setImageResource(R.drawable.bgmalam)
        SalamToast(this).showCustomToast("Malam",this)
    }

    override fun onPause() {
        super.onPause()
    }

    private fun tampilWaktu(){
        var Salam:String;
        var bg:Int;
        when(time){
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
        if(time/60 != this.hours){
            binding.bagroundInnerGame.setImageResource(bg)
            SalamToast(this).showCustomToast(Salam,this)
            this.hours = time/60
        }
    }
}
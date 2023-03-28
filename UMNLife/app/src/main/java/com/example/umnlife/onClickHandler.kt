package com.example.umnlife

import android.app.Activity
import android.content.ClipData.newIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.umnlife.databinding.ActivityInGameBinding

class onClickHandler(val binding: ActivityInGameBinding, val char: Int, val context: Context?): Activity() {
    public var semester = 1
    public var progressMakan = 50
    public var progressTidur = 50
    public var progressMain = 50
    public var progressBljr = 0
    protected var flagMakan = false
    protected var flagTidur = false
    protected var flagMain = false
    private var charTidur: Int = 0
    private var charMain : Int = 0
    private var charMakan: Int = 0
    private var charBljr : Int = 0
    var maxBljr:Double = 100.0
    public var waktuMain = TimerService()
    public var waktuBelajar = TimerService()
    public var waktuMakan = TimerService()
    public var waktuTidur = TimerService()

    init{
        binding.buttonMain.setOnClickListener{onMain()}
        binding.buttonMakan.setOnClickListener{onMakan()}

        when(char){
            R.drawable.char1 -> {
                this.charTidur = R.drawable.char1tidur
                this.charMakan = R.drawable.char1makan
                this.charBljr = R.drawable.char1
                this.charMain = R.drawable.char1
            }
            R.drawable.char2 ->{
                this.charTidur = R.drawable.char2tidur
                this.charMakan = R.drawable.char2makan
                this.charBljr = R.drawable.char2
                this.charMain = R.drawable.char2
            }
            R.drawable.char3 -> {
                this.charTidur = R.drawable.char3tidur
                this.charMakan = R.drawable.char3makan
                this.charBljr = R.drawable.char3
                this.charMain = R.drawable.char3
            }
        }
    }

    fun intervalMain() {
        waktuMain.setInterval({
            runOnUiThread{
                minMain()
                if(progressMain <= 0){
                    progressMain = 0
                }else if(flagMain == true && progressMain < 20){
                    flagMain = false
                    Toast.makeText(context, "Kamu sangat sedih", Toast.LENGTH_LONG).show()
                }else{
                    flagMain = true
                }
            }
        },5000)
    }

    fun intevalTidur() {
        waktuTidur.setInterval({
            runOnUiThread{
                minTidur()
                if(progressTidur <= 0){
                    progressTidur = 0
                }else if(flagTidur == true && progressTidur < 20){
                    flagTidur = false
                    Toast.makeText(context, "Kamu kurang tidur", Toast.LENGTH_LONG).show()

                }else{
                    flagTidur = true
                }
            }
        },15000)
    }

    fun intervalMakan() {
        waktuMakan.setInterval({
            runOnUiThread() {
                minMakan()
                if (progressMakan <= 0) {
                    progressMakan = 0

                } else if (flagMakan == true && progressMakan < 20) {
                    flagMakan = false
                    Toast.makeText(context, "Kamu Lapar", Toast.LENGTH_LONG).show()
                } else {
                    flagMakan = true
                }
            }
        },1000)
    }

    fun onMain(){
        minTidur()
        plusMain()
        binding.character.setImageResource(charMain)
        TimerService.setTimeout({binding.character.setImageResource(char)},3000)
    }

    fun onMakan(){
        plusMakan()
        plusMain()
        binding.character.setImageResource(charMakan)
        TimerService.setTimeout({binding.character.setImageResource(char)},3000)
    }

    fun onTidur(){
        plusTidur()
        minMakan()
        binding.character.setImageResource(charTidur)
        TimerService.setTimeout({binding.character.setImageResource(char)},3000)
    }

    fun onStudy(waktuDo : TimerService){
        minMakan()
        if (progressBljr >= maxBljr){
            progressBljr = 0
        }else{
            progressBljr+=10
        }
        binding.barBelajar.progress = progressBljr
        binding.semester.setText(String.format("Semester %d", semester))
        waktuDo.resetTimeOut()
        binding.character.setImageResource(charBljr)
        TimerService.setTimeout({binding.character.setImageResource(char)},3000)
        waktuDo.setTimeout({Toast.makeText(context, "Kamu DI DO", Toast.LENGTH_LONG).show()},60000)
    }



    fun plusMakan(){
        progressMakan+=20
        if(progressMakan > 100) progressMakan = 100
        binding.barMakan.setProgress(progressMakan)
    }

    fun plusMain(){
        progressMain+=10
        if(progressMain > 100) progressMain = 100
        binding.barMain.setProgress(progressMain)
    }

    fun plusTidur(){
        progressTidur+=60
        if(progressTidur > 100) progressTidur = 100
        binding.barTidur.setProgress(progressTidur)
    }

    fun minTidur(){
        progressTidur-=1
        binding.barTidur.setProgress(progressTidur)
    }

    fun minMakan(){
        progressMakan -= 2
        binding.barMakan.setProgress(progressMakan)
    }

    fun minMain(){
        progressMain-=2
        binding.barMain.setProgress(progressMain)
    }
}
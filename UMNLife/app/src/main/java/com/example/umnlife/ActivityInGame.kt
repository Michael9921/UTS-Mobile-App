package com.example.umnlife

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.umnlife.databinding.ActivityInGameBinding


const val KEY_MAKAN = "makan_key"
const val KEY_TIDUR = "tidur_key"
const val KEY_MAIN = "main_key"
const val KEY_BLJR = "belajar_key"
const val KEY_NAME = "name_key"
const val KEY_CHAR = "char_key"
const val KEY_SEMESTER = "semester_key"
class ActivityInGame: AppCompatActivity() {
    private lateinit var binding: ActivityInGameBinding
    private var time = 0
    private var hours = 0
    private val waktuDo = TimerService()
    private var mMediaPlayer: MediaPlayer? = null
    private var timeDesc:Int = 0
    private var char:Int = 0
    private var name:String? = ""
    private lateinit var clickFUnc: onClickHandler
    private var putarWaktu = TimerService()
    private var saveData: SharedPreferences.Editor? = null
    private var getSaveData: SharedPreferences? = null
    private var saveFlag = true;

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(R.layout.activity_in_game)

        binding = ActivityInGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            clickFUnc.progressMakan = savedInstanceState.getInt(KEY_MAKAN, 50)
            clickFUnc.progressTidur = savedInstanceState.getInt(KEY_TIDUR, 50)
            clickFUnc.progressMain = savedInstanceState.getInt(KEY_MAIN, 50)
            clickFUnc.progressBljr = savedInstanceState.getInt(KEY_BLJR, 0)
            char = savedInstanceState.getInt(KEY_CHAR)
            name = savedInstanceState.getString(KEY_NAME).toString()
            clickFUnc = onClickHandler(binding, char, this)

        }else{
            getSaveData = getSharedPreferences("dataGame", MODE_PRIVATE)
            if(getSaveData?.contains("NAMA") == true){
                defaultValue()
                clickFUnc = onClickHandler(binding, char, this)
                clickFUnc.progressMakan = getSaveData?.getInt("MAKAN", 50)!!
                clickFUnc.progressMain = getSaveData?.getInt("MAIN", 50)!!
                clickFUnc.progressTidur = getSaveData?.getInt("TIDUR", 50)!!
                clickFUnc.progressBljr = getSaveData?.getInt("BLJR", 0)!!
                time = getSaveData?.getInt("TIME", 0)!!
                clickFUnc.semester = getSaveData?.getInt("SEMESTER", 1)!!
            }else{
                defaultValue()
                clickFUnc = onClickHandler(binding, char, this)
            }

        }

        binding.buttonBljr.setOnClickListener{
            clickFUnc.onStudy(this.waktuDo)
            if(clickFUnc.progressBljr == 0){
                val intent =  Intent(this, ActivityQuiz::class.java)
                startActivity(intent)
            }
        }



        binding.buttonTidur.setOnClickListener{
            binding.viewPopUp.visibility = View.VISIBLE
            Frezze()
            val popupWindow = PopupWindow(this)
            val viewPopUp = layoutInflater.inflate(R.layout.popup_alaram_tidur, null) // inflate a new instance
            popupWindow.contentView = viewPopUp

            val languages = resources.getStringArray(R.array.time_alaram_array)
            val adapterItems = ArrayAdapter(this, R.layout.listitems, languages)
            val autoInput: AutoCompleteTextView = viewPopUp.findViewById(R.id.editTextNumberDecimal)
            autoInput.setAdapter(adapterItems)

            val btnAlrm: Button? = viewPopUp.findViewById(R.id.button_alaram)
            val btnCancle : ImageButton? = viewPopUp.findViewById(R.id.button_cancle)

            btnCancle?.setOnClickListener({
                popupWindow.dismiss()
                binding.viewPopUp.visibility = View.GONE
                goAgain()
            })

            btnAlrm?.setOnClickListener {
                popupWindow.dismiss()
                binding.viewPopUp.visibility = View.GONE
                time = (60 * (autoInput.text.toString().toIntOrNull() ?: 0))
                clickFUnc.onTidur()
                goAgain()
            }

            // remove the view from its parent if it has one
            (viewPopUp.parent as? ViewGroup)?.removeView(viewPopUp)
            popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)
        }

        binding.imageButtonInfo.setOnClickListener {
            saveData = getSharedPreferences("dataGame", MODE_PRIVATE).edit()
            saveData?.clear()
            saveData?.apply()
            saveFlag = false
            val Intent =  Intent(this, MainActivity::class.java);
            startActivity(Intent)
        }
    }

    private fun countDownDO() {
        waktuDo.setTimeout({ Toast.makeText(this, "Kamu DI DO", Toast.LENGTH_LONG).show()},60000)
    }

    private fun waktuJalan() {
        putarWaktu.setInterval({
            runOnUiThread {
                plusTime(1)
                val seconds= time%60
                val hours = (time/60)%24
                val nowHours:String = String.format("%02d:%02d",hours,seconds)
                binding.jam.text = nowHours
                tampilWaktu()
            }
        }, 1000)
    }

    fun plusTime(time: Int){
        this.time += time
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

    fun defaultValue(){
        if(intent.getStringExtra("char") != null){
                char = intent.getStringExtra("char")!!.toInt()
        }else{
            char = getSaveData?.getInt("CHAR", R.drawable.char1) ?: R.drawable.char1
        }
        val refChar = when(char){
            R.drawable.char1 -> R.drawable.char1
            R.drawable.char2 -> R.drawable.char2
            else -> R.drawable.char3
        }
        binding.character.setImageResource(refChar)

        if ( intent.getStringExtra("name") != null) {
            name = intent.getStringExtra("name")
        }else{
            name = getSaveData?.getString("NAMA", name).toString()
        }
        binding.name.text = name
        binding.bagroundInnerGame.setImageResource(R.drawable.bgmalam)
        SalamToast(this).showCustomToast("Selamat Malam",this)
        playSound(R.raw.musikmalam)
    }

    private fun tampilWaktu(){
        val salam:String
        val bg:Int
        val sound:Int
        when((time/60)%24){
            in 4 until 10 -> {
                salam ="Selamat Pagi "+ name
                bg = R.drawable.bgpagi
                sound = R.raw.musikpagi
            }
            in 10 until 14 -> {
                salam = "Selamat Siang "+ name
                bg = R.drawable.bgsiang
                sound = R.raw.musiksiang
            }
            in 14 until 16 -> {
                salam = "Selamat Sore "+ name
                bg = R.drawable.bgsore
                sound = R.raw.musiksore
            }
            else -> {
                salam = "Selamat Malam "+ name
                bg = R.drawable.bgmalam
                sound = R.raw.musikmalam
            }
        }
        if(time/60 != this.hours || mMediaPlayer == null){
            binding.bagroundInnerGame.setImageResource(bg)
            SalamToast(this).showCustomToast(salam,this)
            this.hours = time/60

            if(timeDesc != sound){
                playSound(sound)
                timeDesc = sound
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_MAKAN, clickFUnc.progressMakan)
        outState.putInt(KEY_MAIN, clickFUnc.progressMain)
        outState.putInt(KEY_TIDUR, clickFUnc.progressTidur)
        outState.putInt(KEY_BLJR, clickFUnc.progressBljr)
        outState.putInt(KEY_SEMESTER, clickFUnc.semester)
        outState.putInt("TIME", time)
        outState.putString(KEY_NAME, name)
        outState.putInt(KEY_CHAR, char)
    }

    fun goAgain(){
        waktuJalan()
        countDownDO()
        clickFUnc.intervalMakan()
        clickFUnc.intervalMain()
        clickFUnc.intevalTidur()
    }

    fun Frezze(){
        putarWaktu.resetInterval()
        waktuDo.resetTimeOut()
        clickFUnc.waktuMain.resetInterval()
        clickFUnc.waktuTidur.resetInterval()
        clickFUnc.waktuMakan.resetInterval()
    }

    override fun onStart() {
        super.onStart()
        goAgain()
        if(intent.getBooleanExtra("jawaban", false) != null && intent.getBooleanExtra("jawaban",false)){
                clickFUnc.semester+=1
                binding.semester.setText(String.format("Semester %d", clickFUnc.semester))
                clickFUnc.maxBljr = clickFUnc.maxBljr * 1.1
                binding.barBelajar.setMax(clickFUnc.maxBljr.toInt())
        }else{
            binding.semester.setText(String.format("Semester %d", clickFUnc.semester))
        }

    }
    override fun onStop() {
        super.onStop()
        saveData = getSharedPreferences("dataGame", MODE_PRIVATE).edit()
        Frezze()
        mMediaPlayer!!.release()
        mMediaPlayer = null
        if(saveFlag){
            saveData?.putInt("MAKAN", clickFUnc.progressMakan)
            saveData?.putInt("MAIN", clickFUnc.progressMain)
            saveData?.putInt("BLJR", clickFUnc.progressBljr)
            saveData?.putInt("TIDUR", clickFUnc.progressTidur)
            saveData?.putInt("TIME", time)
            saveData?.putInt("SEMESTER", clickFUnc.semester)
            saveData?.putInt("CHAR", char)
            saveData?.putString("NAMA", name)
            saveData?.apply()
        }

    }
}

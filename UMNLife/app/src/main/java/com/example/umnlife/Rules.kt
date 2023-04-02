package com.example.umnlife

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.example.umnlife.databinding.RulesGameBinding

class Rules: AppCompatActivity()  {
    private lateinit var binding : RulesGameBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding = RulesGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButton.setOnClickListener{
            var mMediaPlayer = MediaPlayer.create(this, R.raw.close_book)
            mMediaPlayer!!.start()

            val Intent =  Intent(this, ActivityInGame::class.java);
            startActivity(Intent)
        }
    }
}
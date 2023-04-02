package com.example.umnlife

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umnlife.databinding.ActivityMainBinding
import com.example.umnlife.databinding.ActivityMenangBinding
import com.example.umnlife.databinding.RulesGameBinding

class Menang: AppCompatActivity()  {
    private lateinit var binding:ActivityMenangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding = ActivityMenangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.restartButton.setOnClickListener{
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.example.umnlife

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umnlife.databinding.ActivityGameOverBinding
import com.example.umnlife.databinding.ActivityMainBinding
import com.example.umnlife.databinding.ActivityMenangBinding
import com.example.umnlife.databinding.RulesGameBinding

class Kalah: AppCompatActivity()  {
    private lateinit var binding:ActivityGameOverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.restartButton.setOnClickListener{
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.pesanKalah.text = intent.getStringExtra("alasan")
    }
}
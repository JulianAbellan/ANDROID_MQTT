package com.example.learnwithgarbancete

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import java.util.*

class HealthyUnhealthy : AppCompatActivity() {

    lateinit var backButton2: Button
    lateinit var texttospeech: TextToSpeech
    lateinit var settings : SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthy_unhealthy)
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        texttospeech = TextToSpeech(this, TextToSpeech.OnInitListener {

            val idioma = settings.getString("idioma", "").toString()

            if(idioma.equals("ENGLISH")){
                texttospeech.setLanguage(Locale.ENGLISH)
            }
            if(idioma.equals("ESPAÑOL")){
                val locSpanish = Locale("spa", "ES")
                texttospeech.setLanguage(locSpanish)
            }

        })

        backButton2 = findViewById(R.id.backButton3)

        backButton2.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            texttospeech.speak(getText(R.string.back).toString(), TextToSpeech.QUEUE_ADD, null);

            startActivity(intent)
        }

    }

}
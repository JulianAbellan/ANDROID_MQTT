package com.example.learnwithgarbancete

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import java.util.*

class InfoActivity : AppCompatActivity() {

    lateinit var backButton3: Button
    lateinit var texttospeech: TextToSpeech
    lateinit var settings : SharedPreferences
    lateinit var idioma: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        texttospeech = TextToSpeech(this, TextToSpeech.OnInitListener {

            idioma = settings.getString("idioma", "").toString()

            if(idioma.equals("ENGLISH")){
                texttospeech.setLanguage(Locale.ENGLISH)
            }

            if(idioma.equals("ESPAÑOL")){
                val locSpanish = Locale("spa", "ES")
                texttospeech.setLanguage(locSpanish)
            }
        })

        backButton3 = findViewById(R.id.backButton3)

        backButton3.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(getText(R.string.back).toString(), TextToSpeech.QUEUE_ADD, null);

            startActivity(intent)
        }

    }
}
package com.example.learnwithgarbancete

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.ColorMatrixColorFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageView
import java.util.*

class HealthyUnhealthy : AppCompatActivity() {

    lateinit var backButton2: Button
    lateinit var flecharoja: ImageView
    lateinit var flechaverde: ImageView
    lateinit var fotocentro: ImageView
    lateinit var garbancete: Button

    lateinit var texttospeech: TextToSpeech
    lateinit var settings : SharedPreferences
    lateinit var tipodaltonico : String



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
        flecharoja = findViewById(R.id.flecharoja)
        flechaverde = findViewById(R.id.flechaverde)
        garbancete = findViewById(R.id.garbancete)
        fotocentro = findViewById(R.id.imageView)

        tipodaltonico = settings.getString("daltonico", "").toString()
        aplicarTipoDaltonismo(tipodaltonico)

        backButton2.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            texttospeech.speak(getText(R.string.back).toString(), TextToSpeech.QUEUE_ADD, null);

            startActivity(intent)
        }

    }

    fun aplicarTipoDaltonismo(dalt: String) {

        val protanomalia = floatArrayOf(
            0.567f, 0.433f, 0.0f, 0.0f, 0f,
            0.558f, 0.442f, 0.0f, 0.0f, 0f,
            0.0f, 0.242f, 0.758f, 0.0f, 0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0f
        )

        val deuteronomalia = floatArrayOf(
            0.625f, 0.375f, 0.0f, 0.0f, 0f,
            0.7f, 0.3f, 0.0f, 0.0f, 0f,
            0.0f, 0.3f, 0.7f, 0.0f, 0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0f
        )

        val tritanomalia = floatArrayOf(
            0.95f, 0.05f, 0.0f, 0.0f, 0f,
            0.0f, 0.433f, 0.567f, 0.0f, 0f,
            0.0f, 0.475f, 0.525f, 0.0f, 0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0f
        )

        var matrix = floatArrayOf(
            1f, 0f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )

        if (dalt.equals("Protanomalia")){
            matrix = protanomalia
        }else if(dalt.equals("Deuteronomalia")){
            matrix = deuteronomalia
        }else if(dalt.equals("Tritanomalia")){
            matrix = tritanomalia
        }

        backButton2 = findViewById(R.id.backButton3)
        flecharoja.colorFilter = ColorMatrixColorFilter(matrix)
        flechaverde.colorFilter = ColorMatrixColorFilter(matrix)
        fotocentro.colorFilter = ColorMatrixColorFilter(matrix)
        garbancete.background.colorFilter = ColorMatrixColorFilter(matrix)
    }

}
package com.example.learnwithgarbancete

import android.R.color
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.ColorMatrixColorFilter
import android.os.*
import android.speech.tts.TextToSpeech
import android.support.v7.app.*
import android.view.*
import android.widget.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var texto: TextView
    lateinit var maths: Button;
    lateinit var language : Button;
    lateinit var options : Button;
    lateinit var science: Button;
    lateinit var health: Button;
    lateinit var geometry: Button;
    lateinit var info: Button;
    lateinit var settings : SharedPreferences
    lateinit var conf : ConfigurationActivity
    lateinit var edit : SharedPreferences.Editor
    lateinit var texttospeech: TextToSpeech
    lateinit var idioma: String
    lateinit var image_background: ImageView
    lateinit var bocadillo: ImageView
    lateinit var garbancete: Button
    lateinit var fondo : ImageView
    lateinit var tipodaltonico : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        settings = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)

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

        maths = findViewById(R.id.mathsButton)
        language = findViewById(R.id.languageButton)
        options = findViewById(R.id.configuration)
        conf = ConfigurationActivity()
        science = findViewById(R.id.science)
        health = findViewById(R.id.health)
        geometry = findViewById(R.id.geometry)
        info = findViewById(R.id.info)
        image_background = findViewById(R.id.imageView)
        garbancete = findViewById(R.id.garbancete)
        bocadillo = findViewById(R.id.imageView6)
        fondo = findViewById(R.id.fondo)

        tipodaltonico = settings.getString("daltonico", "").toString()
        aplicarTipoDaltonismo(tipodaltonico)

        language.setOnClickListener(){
            val auxText = language.text.toString()
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(auxText, TextToSpeech.QUEUE_ADD, null);

            val intent : Intent = Intent(this, LanguageGame::class.java)
            startActivity(intent)
        }

        options.setOnClickListener() {
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(getString(R.string.options).toString(), TextToSpeech.QUEUE_ADD, null);

            val intent : Intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
        }

        maths.setOnClickListener() {
            val auxText = getString(R.string.maths_name2)
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(auxText, TextToSpeech.QUEUE_ADD, null);

            val intent : Intent = Intent(this, MathsGame::class.java)
            startActivity(intent)
        }

        science.setOnClickListener(){
            val auxText = science.text.toString()
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(auxText, TextToSpeech.QUEUE_ADD, null);

            //val intent : Intent = Intent(this, LanguageGame::class.java)
            //startActivity(intent)
        }
        health.setOnClickListener(){
            val auxText = health.text.toString()
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(auxText, TextToSpeech.QUEUE_ADD, null);

            val intent : Intent = Intent(this, HealthyUnhealthy::class.java)
            startActivity(intent)
        }
        geometry.setOnClickListener(){
            val auxText = geometry.text.toString()
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(auxText, TextToSpeech.QUEUE_ADD, null);

            val intent : Intent = Intent(this, GeometryGame::class.java)
            startActivity(intent)
        }
        info.setOnClickListener(){
            val auxText = getString(R.string.info)
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(auxText, TextToSpeech.QUEUE_ADD, null);

            val intent : Intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

    }

    fun cargarpreferencias() {
        var idioma = settings.getString("idioma", "ENGLISH").toString()
        chooseLanguage(idioma)
    }

    fun chooseLanguage(l: String) {
        var lang = ""
        var country = ""

        if (l.equals("ESPAÑOL")) {
            lang = "es"
            country = "ES"

        } else if (l.equals("ENGLISH")) {
            lang = "en"
            country = "EN"
        }


        val localizacion = Locale(lang, country)

        Locale.setDefault(localizacion)
        val config = Configuration()
        config.locale = localizacion
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
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

        fondo.colorFilter = ColorMatrixColorFilter(matrix)
        image_background.colorFilter = ColorMatrixColorFilter(matrix)
        bocadillo.colorFilter = ColorMatrixColorFilter(matrix)
        garbancete.background.colorFilter = ColorMatrixColorFilter(matrix)
        maths.background.colorFilter = ColorMatrixColorFilter(matrix)
        language.background.colorFilter = ColorMatrixColorFilter(matrix)
        options.background.colorFilter = ColorMatrixColorFilter(matrix)
        science.background.colorFilter = ColorMatrixColorFilter(matrix)
        health.background.colorFilter = ColorMatrixColorFilter(matrix)
        geometry.background.colorFilter = ColorMatrixColorFilter(matrix)
        info.background.colorFilter = ColorMatrixColorFilter(matrix)

    }
}
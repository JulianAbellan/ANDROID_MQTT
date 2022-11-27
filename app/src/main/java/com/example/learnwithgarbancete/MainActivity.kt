package com.example.learnwithgarbancete

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.support.v7.app.*
import android.os.*
import android.speech.tts.TextToSpeech
import android.widget.*
import android.view.*
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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


        maths = findViewById(R.id.mathsButton)
        language = findViewById(R.id.languageButton)
        options = findViewById(R.id.configuration)
        conf = ConfigurationActivity()
        science = findViewById(R.id.science)
        health = findViewById(R.id.health)
        geometry = findViewById(R.id.geometry)
        info = findViewById(R.id.info)

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
            val auxText = maths.text.toString()
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
}
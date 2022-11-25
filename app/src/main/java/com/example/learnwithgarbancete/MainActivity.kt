package com.example.learnwithgarbancete

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.support.v7.app.*
import android.os.*
import android.widget.*
import android.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var texto: TextView
    lateinit var maths: Button;
    lateinit var language : Button;
    lateinit var options : Button;
    lateinit var settings : SharedPreferences
    lateinit var conf : ConfigurationActivity
    lateinit var edit : SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        maths = findViewById(R.id.mathsButton)
        language = findViewById(R.id.languageButton)
        options = findViewById(R.id.configuration)
        conf = ConfigurationActivity()

        language.setOnClickListener(){
            val intent : Intent = Intent(this, LanguageGame::class.java)
            startActivity(intent)
        }

        options.setOnClickListener() {
            val intent : Intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
        }

        maths.setOnClickListener() {
            val intent : Intent = Intent(this, MathsGame::class.java)
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
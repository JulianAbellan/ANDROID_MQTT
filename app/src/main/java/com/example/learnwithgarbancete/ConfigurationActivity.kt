package com.example.learnwithgarbancete

import android.annotation.SuppressLint

import android.content.*
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import java.io.*
import java.util.*

class ConfigurationActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var leftLanguage: Button
    lateinit var rightLanguage: Button
    lateinit var leftDalt: Button
    lateinit var rightDalt: Button
    lateinit var language: TextView
    lateinit var settings: SharedPreferences
    lateinit var idioma: String
    lateinit var colorblind: TextView
    lateinit var daltonico: String
    lateinit var vpd: Button
    lateinit var edit: SharedPreferences.Editor
    lateinit var garbancete: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration2)

        button = findViewById(R.id.buttonBack)
        vpd = findViewById(R.id.RestoreDefaultSettingsBtn)
        leftLanguage = findViewById(R.id.leftLanguageMode)
        rightLanguage = findViewById(R.id.rightLanguageMode)
        leftDalt = findViewById(R.id.leftDaltMode)
        rightDalt = findViewById(R.id.rightDaltMode)
        language = findViewById(R.id.languageSelect)
        colorblind = findViewById(R.id.colorBlindnessMode)
        garbancete = findViewById(R.id.garbanConf)

        cargarpreferencias()
        edit = settings.edit()

        button.setOnClickListener() {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        leftLanguage.setOnClickListener() {
            setLanguage()
        }

        rightLanguage.setOnClickListener() {
            setLanguage()
        }

        rightDalt.setOnClickListener() {
            swapDalt()
        }

        leftDalt.setOnClickListener() {
            swapDalt()
        }

        vpd.setOnClickListener() {
            establecerValoresPorDefecto()
        }

        garbancete.setOnClickListener(){
            activarTutorial()
        }

        checkTutorial()
    }

    fun checkTutorial(){
        var tuto = settings.getString("tutorial", "si").toString()
        println(tuto)
        if (tuto.equals("si")){
            activarTutorial()
        }
    }

    fun activarTutorial(){
        edit.putString("tutorial","no")
        edit.commit()

        val intent: Intent = Intent(this, Tutorial::class.java)
        startActivity(intent)
    }

    fun swapDalt() {
        if (colorblind.getText().equals("ON")) {
            cargarDaltonico("OFF")
            guardarpreferenciasDalt("OFF")
        } else if (colorblind.getText().equals("OFF")) {
            cargarDaltonico("ON")
            guardarpreferenciasDalt("ON")
        }
    }

    fun cargarpreferencias() {
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        idioma = settings.getString("idioma", "ENGLISH").toString()
        chooseLanguage(idioma)

        daltonico = settings.getString("daltonico", "OFF").toString()
        cargarDaltonico(daltonico)
    }

    fun cargarDaltonico(daltonico: String) {
        if (daltonico.equals("ON")) {
            colorblind.setText("ON")
        } else {
            colorblind.setText("OFF")
        }
    }

    fun cargarpreferencias2(): String {

        idioma = settings.getString("idioma", "").toString()
        return idioma
    }

    fun establecerValoresPorDefecto() {
        chooseLanguage("ENGLISH")
    }

    fun guardarpreferenciasIdioma(l: String) {
        var edit = settings.edit()
        edit.putString("idioma", l)
        edit.commit()
        edit.apply()

    }

    fun guardarpreferenciasDalt(l: String) {
        var edit = settings.edit()
        edit.putString("daltonico", l)
        edit.commit()
        edit.apply()

    }


    fun setLanguage() {
        val l = language.getText()

        if (l.equals("ESPAÑOL")) {
            chooseLanguage("ENGLISH")
        } else if (l.equals("ENGLISH")) {
            chooseLanguage("ESPAÑOL")
        } else {
            language.setText(cargarpreferencias2())
            cargarpreferencias()
        }

        val intent : Intent = Intent(this, Auxiliar::class.java)
        startActivity(intent)
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

        guardarpreferenciasIdioma(l)
        language.setText(l)
    }
}



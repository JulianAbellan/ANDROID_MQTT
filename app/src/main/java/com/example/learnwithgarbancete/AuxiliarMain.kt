package com.example.learnwithgarbancete

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class AuxiliarMain : AppCompatActivity() {


    lateinit var settings : SharedPreferences
    lateinit var conf : ConfigurationActivity
    lateinit var edit : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auxiliar_main)

        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        edit = settings.edit()
        cargarpreferencias()

        val intent : Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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
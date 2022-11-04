package com.example.learnwithgarbancete

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import java.util.*

class ConfigurationActivity : AppCompatActivity() {

    lateinit var switchlanguage: Switch
    lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration2)

        switchlanguage = findViewById(R.id.switchlanguage)
        button = findViewById(R.id.buttonBack)

        button.setOnClickListener(){
            val intent : Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        switchlanguage.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val localizacion = Locale("es", "ES")

                Locale.setDefault(localizacion)
                val config = Configuration()
                config.locale = localizacion
                baseContext.resources.updateConfiguration(
                    config,
                    baseContext.resources.displayMetrics
                )
            } else {
                val localizacion = Locale("en", "EN")

                Locale.setDefault(localizacion)
                val config = Configuration()
                config.locale = localizacion
                baseContext.resources.updateConfiguration(
                    config,
                    baseContext.resources.displayMetrics
                )
            }
        }
    }
}
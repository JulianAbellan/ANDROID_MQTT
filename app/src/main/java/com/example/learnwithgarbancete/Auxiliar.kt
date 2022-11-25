package com.example.learnwithgarbancete

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Auxiliar : AppCompatActivity() {

    lateinit var boton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auxiliar)

        boton = findViewById(R.id.okboton)
        val intent: Intent = Intent(this, ConfigurationActivity::class.java)
        startActivity(intent)

    }
}
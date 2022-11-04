package com.example.learnwithgarbancete

import android.content.Intent
import android.support.v7.app.*
import android.os.*
import android.widget.*
import android.view.*

class MainActivity : AppCompatActivity() {

    lateinit var maths: Button;
    lateinit var text: TextView;
    lateinit var language : Button;
    lateinit var options : Button;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        maths = findViewById(R.id.mathsButton)
        language = findViewById(R.id.languageButton)
        options = findViewById(R.id.configuration)

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
}
package com.example.learnwithgarbancete

import android.support.v7.app.*
import android.os.*
import android.widget.*
import android.view.*

class MainActivity : AppCompatActivity() {

    lateinit var maths: Button;
    lateinit var text: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        maths = findViewById(R.id.mathsButton)
        language = findViewById(R.id.languageButton)

        language.setOnClickListener(){
            val intent : Intent = Intent(this, LanguageGame::class.java)
            startActivity(intent)
        }

        maths.setOnClickListener() {
            val intent : Intent = Intent(this, MathsGame::class.java)
            startActivity(intent)
        }
    }
}
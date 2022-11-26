package com.example.learnwithgarbancete

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class InfoActivity : AppCompatActivity() {

    lateinit var backButton3: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)


        backButton3 = findViewById(R.id.backButton3)

        backButton3.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
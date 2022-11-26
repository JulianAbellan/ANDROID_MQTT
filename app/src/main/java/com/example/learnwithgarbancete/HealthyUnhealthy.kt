package com.example.learnwithgarbancete

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HealthyUnhealthy : AppCompatActivity() {

    lateinit var backButton2: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthy_unhealthy)


        backButton2 = findViewById(R.id.backButton3)

        backButton2.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}
package com.example.learnwithgarbancete

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class Tutorial : AppCompatActivity() {

    lateinit var garbancete : ImageView;
    lateinit var output : TextView;
    lateinit var flecha1 : ImageView;
    lateinit var flecha2 : ImageView;
    lateinit var flecha3 : ImageView;
    lateinit var flecha4 : ImageView;
    lateinit var saltar : Button
    lateinit var siguiente : Button
    var i : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        garbancete = findViewById(R.id.garbanTuto);
        output = findViewById(R.id.outputTuto);
        flecha1 = findViewById(R.id.flecha1);
        flecha2 = findViewById(R.id.flecha2);
        flecha3 = findViewById(R.id.flecha3);
        flecha4 = findViewById(R.id.flecha4);
        saltar = findViewById(R.id.skipb)
        siguiente = findViewById(R.id.siguiente)

        saltar.setOnClickListener(){
            val intent: Intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
        }

        output.setText(getString(R.string.saludo))

        siguiente.setOnClickListener(){
            rutina(i)
            i++
        }
    }

    fun rutina(i : Int){

        when(i){
            0 ->
                output.setText(getString(R.string.dialogo1))
            1 -> {
                flecha1.visibility = View.VISIBLE
                output.setText(getString(R.string.dialogo2))
            }
            2 -> {
                flecha1.visibility = View.INVISIBLE
                flecha2.visibility = View.VISIBLE
                output.setText(getString(R.string.dialogo3))
            }
            3 -> {
                flecha2.visibility = View.INVISIBLE
                flecha3.visibility = View.VISIBLE
                output.setText(getString(R.string.dialogo4))
            }
            4 -> {
                flecha3.visibility = View.INVISIBLE
                flecha4.visibility = View.VISIBLE
                output.setText(getString(R.string.dialogo5))
            }
            5 -> {
                flecha4.visibility = View.INVISIBLE
                output.setText(getString(R.string.despedida))
            }
            else -> {
                val intent: Intent = Intent(this, ConfigurationActivity::class.java)
                startActivity(intent)
            }

        }

    }
}
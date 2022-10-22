package com.example.learnwithgarbancete

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.Random

class MathsGame : AppCompatActivity() {

    var operandoA : Int = 0
    var operandoB : Int = 0
    var operacion : Int = 0
    var resultado : Int = 0
    var operacionText : TextView? = null
    lateinit var atrasMaths : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maths_game)

        operacionText = findViewById(R.id.operacionText)
        mostrarOp()


        atrasMaths = findViewById(R.id.atrasMaths)
        atrasMaths.setOnClickListener() {
            val intent : Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun mostrarOp () {
        operacionText?.setText(generarOp())
    }

    fun generarOp (): String {
        var operandoA = Random().nextInt(9) + 1
        var operandoB = Random().nextInt(9) + 1
        var operacion = Random().nextInt(2)

        if (operandoB > operandoA) {
            var aux = operandoA
            operandoA = operandoB
            operandoB = aux
        }

        when (operacion) {
            0 -> {
                resultado = operandoA + operandoB
                return "$operandoA + $operandoB = ?"
            }
            1 -> {
                resultado = operandoA - operandoB
                return "$operandoA - $operandoB = ?"
            }
            else -> throw Exception("Ha habido un error en la operacion. No es 0 ó 1")
        }
    }
}
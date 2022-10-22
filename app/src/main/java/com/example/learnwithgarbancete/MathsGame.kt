package com.example.learnwithgarbancete

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.Random

class MathsGame : AppCompatActivity() {

    var operandoA: Int = 0
    var operandoB: Int = 0
    var operacion: Int = 0
    var resultado: Int = 0
    lateinit var lista : List<Int>
    var operacionText: TextView? = null
    lateinit var atrasMaths: Button
    lateinit var buttonOpA: Button
    lateinit var buttonOpB: Button
    lateinit var buttonOpC: Button
    lateinit var buttonOpD: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maths_game)

        operacionText = findViewById(R.id.operacionText)
        mostrarOp()
        crearOpciones()

        atrasMaths = findViewById(R.id.atrasMaths)

        atrasMaths.setOnClickListener() {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun mostrarOp() {
        operacionText?.setText(generarOp())
    }

    fun setOperaciones(){
        buttonOpA.setText(lista[0])
        buttonOpB.setText(lista[1])
        buttonOpC.setText(lista[2])
        buttonOpD.setText(lista[3])
    }

    fun crearOpciones() {
        var num1 = 0
        var num2 = 0
        var num3 = 0

        do {
            num1 = resultado + Random().nextInt(6) - 3
            num2 = resultado + Random().nextInt(6) - 3
            num3 = resultado + Random().nextInt(6) - 3
        } while (!distintos(num1, num2, num3))

        lista = listOf(resultado, num1, num2, num3).shuffled()

        setOperaciones()
    }

    fun distintos(num1 : Int, num2 : Int, num3 : Int) : Boolean{
       return (num1 != num2) && (num1 != num3) && (num1 != resultado) && (num2 != num3) && (num2 != resultado) && (num3 != resultado)
    }

    fun generarOp(): String {
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
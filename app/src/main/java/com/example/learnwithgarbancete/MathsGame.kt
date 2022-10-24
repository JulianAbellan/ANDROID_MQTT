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
    var scorePuntos: Int = 0
    var i: Int = 0
    lateinit var lista: List<Int>
    var strOp: String? = null
    var operacionText: TextView? = null
    var nivelText: TextView? = null
    var score: TextView? = null
    var resumenText: TextView? = null
    lateinit var atrasMaths: Button
    lateinit var buttonOpA: Button
    lateinit var buttonOpB: Button
    lateinit var buttonOpC: Button
    lateinit var buttonOpD: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maths_game)


        operacionText = findViewById(R.id.operacionText)
        nivelText = findViewById(R.id.nivelText)
        score = findViewById(R.id.score)
        buttonOpA = findViewById(R.id.buttonOpA)
        buttonOpB = findViewById(R.id.buttonOpB)
        buttonOpC = findViewById(R.id.buttonOpC)
        buttonOpD = findViewById(R.id.buttonOpD)
        atrasMaths = findViewById(R.id.atrasMaths)
        resumenText = findViewById(R.id.resumenText)

        atrasMaths.setOnClickListener() {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonOpA.setOnClickListener() {
            if (resultado == lista[0]) {
                operacionText?.setText("CORRECT")
            } else {
                operacionText?.setText("INCORRECT")
            }
            checkResults()
        }

        buttonOpB.setOnClickListener() {
            if (resultado == lista[1]) {
                operacionText?.setText("CORRECT")
            } else {
                operacionText?.setText("INCORRECT")
            }
            checkResults()
        }

        buttonOpC.setOnClickListener() {
            if (resultado == lista[2]) {
                operacionText?.setText("CORRECT")
            } else {
                operacionText?.setText("INCORRECT")
            }
            checkResults()
        }

        buttonOpD.setOnClickListener() {
            if (resultado == lista[3]) {
                operacionText?.setText("CORRECT")
            } else {
                operacionText?.setText("INCORRECT")
            }
            checkResults()
        }

        rutina()
    }


    fun rutina() {
        i+=1
        ponerNivel()
        setScore()
        mostrarOp()
        crearOpciones()
        setOperaciones()
    }

    fun checkResults(){
        if(operacionText?.getText()?.equals("CORRECT") == true) {
            scorePuntos +=1
        }

        rutina()
    }

    fun setScore(){
        score?.setText("SCORE: ${scorePuntos}")
    }

    fun ponerNivel(){
        nivelText?.setText("LEVEL: ${i}/5")

        if(i == 5){
            resumen()
        }
    }

    fun resumen() {
        buttonOpA.setEnabled(false)
        buttonOpB.setEnabled(false)
        buttonOpC.setEnabled(false)
        buttonOpD.setEnabled(false)

        if (scorePuntos < 2)
            resumenText?.setText("YOU GOT ${scorePuntos} POINTS...")
        else if (scorePuntos < 5)
            resumenText?.setText("WOW! YOU GOT ${scorePuntos} POINTS! YOU ALMOST GOT IT!")
        else
            resumenText?.setText("CONGRATS! YOU GOT ${scorePuntos} POINTS! YOU ARE A REAL MATHEMATICIAN")
    }

    fun mostrarOp() {
        operacionText?.setText(generarOp())
    }

    fun setOperaciones() {
        buttonOpA.setText("" + lista[0])
        buttonOpB.setText("" + lista[1])
        buttonOpC.setText("" + lista[2])
        buttonOpD.setText("" + lista[3])
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

    }

    fun distintos(num1: Int, num2: Int, num3: Int): Boolean {
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
                strOp  ="$operandoA + $operandoB = ?"
                return strOp as String
            }
            1 -> {
                resultado = operandoA - operandoB
                strOp = "$operandoA - $operandoB = ?"
                return strOp as String
            }
            else -> throw Exception("Ha habido un error en la operacion. No es 0 ó 1")
        }
    }
}
package com.example.learnwithgarbancete

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.*
import java.util.*

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
    lateinit var settings : SharedPreferences
    lateinit var texttospeech: TextToSpeech
    lateinit var idioma: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maths_game)
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        texttospeech = TextToSpeech(this, TextToSpeech.OnInitListener {

            idioma = settings.getString("idioma", "").toString()

            if(idioma.equals("ENGLISH")){
                texttospeech.setLanguage(Locale.ENGLISH)
            }

            if(idioma.equals("ESPAÑOL")){
                val locSpanish = Locale("spa", "ES")
                texttospeech.setLanguage(locSpanish)
            }
        })

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
            texttospeech.speak(getString(R.string.back).toString(), TextToSpeech.QUEUE_ADD, null);
            startActivity(intent)
        }

        buttonOpA.setOnClickListener() {
            if(Integer.parseInt(buttonOpA.getText() as String) == resultado){
                checkResults(true)
            } else {
                checkResults(false)
            }
        }

        buttonOpB.setOnClickListener() {
            if(Integer.parseInt(buttonOpB.getText() as String) == resultado){
                checkResults(true)
            } else {
                checkResults(false)
            }
        }

        buttonOpC.setOnClickListener() {
            if(Integer.parseInt(buttonOpC.getText() as String) == resultado){
                checkResults(true)
            } else {
                checkResults(false)
            }
        }

        buttonOpD.setOnClickListener() {
            if(Integer.parseInt(buttonOpD.getText() as String) == resultado){
                checkResults(true)
            } else {
                checkResults(false)
            }
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

    fun checkResults(correct: Boolean){
        if(correct) {
            resumenText?.setText("${getString(R.string.correct)}")
            scorePuntos += 1
        } else {
            resumenText?.setText("${getString(R.string.incorrect)}")
        }


        rutina()
    }

    fun setScore(){
        score?.setText("${getString(R.string.score_name)}: ${scorePuntos}")
    }

    fun ponerNivel(){
        nivelText?.setText("${getString(R.string.level_name)}: ${i}/5")

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
            resumenText?.setText("${getString(R.string.puntuaje_bajo)} ${scorePuntos} ${getString(R.string.points)}")
        else if (scorePuntos < 5)
            resumenText?.setText("${getString(R.string.puntuaje_medio)} ${scorePuntos} ${getString(R.string.points)}")
        else
            resumenText?.setText("${getString(R.string.puntuaje_alto)} ${scorePuntos} ${getString(R.string.points)}")
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
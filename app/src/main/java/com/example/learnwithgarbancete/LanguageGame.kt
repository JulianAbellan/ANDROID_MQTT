package com.example.learnwithgarbancete

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.*
import android.widget.*
import java.util.*

class LanguageGame : AppCompatActivity() {

    lateinit var fraseActual: String
    lateinit var backButton: Button
    lateinit var grabar: ImageButton
    lateinit var grabarText: TextView
    lateinit var nivelTextLengua : TextView
    lateinit var dataLanguage : TextView
    lateinit var frases : List<String>
    var inputText : String? = null
    val RECOGNISE_SPEECH_ACTIVITY = 102
    var i : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_game)

        backButton = findViewById(R.id.backButton)
        grabarText = findViewById(R.id.grabarTexto)
        nivelTextLengua = findViewById(R.id.nivelTextLengua)
        dataLanguage = findViewById(R.id.dataLanguage)
        grabar = findViewById(R.id.grabarButton)

        grabar.setOnClickListener(){
           askSpeechInput()
        }

        backButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        generarFrases()
        rutina()

    }

    fun validar(){

        if (!inputText.equals(fraseActual, ignoreCase = true)){
            grabarText.setText("${getString(R.string.tryagain)}")
        } else {
            grabarText.setText("${getString(R.string.correct)}")
            i+=1

            if (i >= 5) {
                finalizar()
            } else {
                rutina()
            }
        }

    }

    fun finalizar(){
        grabar.setEnabled(false)
        dataLanguage.setText("${getString(R.string.successLanguage)}")
    }

    fun rutina(){
        setNivel()
        ponerFrase()
    }

    fun ponerFrase(){
        dataLanguage.setText("${getString(R.string.say)}: ${frases?.get(i)}")

        fraseActual = frases.get(i)
        if (fraseActual.get(0) == '¿' || fraseActual.get(0) == '¡'){
            fraseActual = (fraseActual.substring(1, fraseActual.length-1))
        }

    }

    fun setNivel(){
        nivelTextLengua.setText("${getString(R.string.level_name)}: ${i+1}/5")
    }

    fun generarFrases(){
        frases = listOf(getString(R.string.frase1), getString(R.string.frase2), getString(R.string.frase3), getString(R.string.frase4), getString(R.string.frase5), getString(R.string.frase6), getString(R.string.frase7), getString(R.string.frase8), getString(R.string.frase9), getString(R.string.frase10)).shuffled()
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RECOGNISE_SPEECH_ACTIVITY && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            inputText = result?.get(0).toString()
            validar()
        }
    }



    fun askSpeechInput() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this, "Your device does not support voice recognision", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "${getString(R.string.idioma)}")
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "${getString(R.string.saysmth)}")
            startActivityForResult(i, RECOGNISE_SPEECH_ACTIVITY)
        }
    }


}
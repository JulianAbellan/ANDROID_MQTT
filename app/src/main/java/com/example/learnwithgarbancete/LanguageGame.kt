package com.example.learnwithgarbancete

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.*
import android.widget.*
import java.util.*

class LanguageGame : AppCompatActivity() {

    lateinit var sendMsg: Button
    lateinit var backButton: Button
    lateinit var grabar: ImageButton
    lateinit var grabarText: TextView
    lateinit var nivelTextLengua : TextView
    lateinit var dataLanguage : TextView
    var frases : List<String>? = null
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
        sendMsg = findViewById(R.id.sendMsg)

        sendMsg.setEnabled(false)

        sendMsg.setOnClickListener(){
            validar()
        }

        grabar.setOnClickListener(){
           askSpeechInput()
        }

        backButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        rutina()



    }

    fun validar(){
        if (inputText != frases?.get(i)) {
            grabarText.setText("Try again!")
        } else {
            grabarText.setText("CORRECT!")
        }
    }

    fun rutina(){
        setNivel()
        generarFrases()
        ponerFrase()
    }

    fun ponerFrase(){
        dataLanguage.setText("SAY: ${frases?.get(i)}")
    }

    fun setNivel(){
        nivelTextLengua.setText("LEVEL: ${i+1}/5")
    }

    fun generarFrases(){
        var lista = listOf("¡Hola!", "¿Cómo te llamas?", "Mi nombre es Garbancete", "Me gusta jugar al fútbol", "Estoy muy feliz", "Soy un conejo")
        frases = lista.shuffled().subList(0, 4)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RECOGNISE_SPEECH_ACTIVITY && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            inputText = result?.get(0).toString()
            sendMsg.setEnabled(true)
        }
    }



    fun askSpeechInput() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this, "Your device does not support voice recognision", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES")
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!")
            startActivityForResult(i, RECOGNISE_SPEECH_ACTIVITY)
        }
    }


}
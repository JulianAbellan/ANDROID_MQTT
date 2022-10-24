package com.example.learnwithgarbancete

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.*
import android.widget.*
import java.util.*

class LanguageGame : AppCompatActivity() {

    lateinit var backButton: Button
    lateinit var grabar: ImageButton
    lateinit var grabarText: TextView
    val RECOGNISE_SPEECH_ACTIVITY = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_game)

        backButton = findViewById(R.id.backButton)
        grabarText = findViewById(R.id.grabarTexto)
        grabar = findViewById(R.id.grabarButton)

        grabar.setOnClickListener(){
           askSpeechInput()
        }

        backButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RECOGNISE_SPEECH_ACTIVITY && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            grabarText.setText(result?.get(0).toString())
        }
    }



    fun askSpeechInput() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this, "Your device does not support voice recognision", Toast.LENGTH_SHORT).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!")
            startActivityForResult(i, RECOGNISE_SPEECH_ACTIVITY)
        }
    }


}
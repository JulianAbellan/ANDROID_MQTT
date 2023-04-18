package com.example.learnwithgarbancete

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.ColorMatrixColorFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.*
import android.speech.tts.TextToSpeech
import android.widget.*
import java.util.*

class LanguageGame : AppCompatActivity() {

    lateinit var fraseActual: String
    lateinit var backButton: Button
    lateinit var grabar: Button
    lateinit var grabarText: TextView
    lateinit var nivelTextLengua : TextView
    lateinit var dataLanguage : TextView
    lateinit var frases : List<String>
    lateinit var texttospeech: TextToSpeech
    lateinit var settings : SharedPreferences

    var mqttCliente : MQTTCliente = MQTTCliente()
    var inputText : String? = null
    val RECOGNISE_SPEECH_ACTIVITY = 102
    var i : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_game)
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        texttospeech = TextToSpeech(this, TextToSpeech.OnInitListener {

            val idioma = settings.getString("idioma", "").toString()

            if(idioma.equals("ENGLISH")){
                texttospeech.setLanguage(Locale.ENGLISH)
            }
            if(idioma.equals("ESPAÑOL")){
                val locSpanish = Locale("spa", "ES")
                texttospeech.setLanguage(locSpanish)
            }

        })
        backButton = findViewById(R.id.backButton3)
        grabarText = findViewById(R.id.grabarTexto)
        nivelTextLengua = findViewById(R.id.nivelTextLengua)
        dataLanguage = findViewById(R.id.dataLanguage)
        grabar = findViewById(R.id.grabarButton)

        grabar.setOnClickListener(){
           askSpeechInput()
        }

        backButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(getText(R.string.back).toString(), TextToSpeech.QUEUE_ADD, null);

            startActivity(intent)
        }

        aplicardalt()
        establecerConexion()
        generarFrases()
        rutina()

    }

    fun establecerConexion(){
        mqttCliente.connect(applicationContext)
    }

    fun validar(){

        if (!inputText.equals(fraseActual, ignoreCase = true)){
            grabarText.setText("${getString(R.string.tryagain)}")
            mqttCliente.publish("juego/idioma", "FALLO. Se esperaba $fraseActual")
        } else {
            grabarText.setText("${getString(R.string.correct)}")
            mqttCliente.publish("juego/idioma", "CORRECTO. Esperando siguiente respuesta...")
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

        if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(dataLanguage.text as String?, TextToSpeech.QUEUE_ADD, null);

        var valor = 0

        if(settings.getString("idioma", "ENGLISH").equals("ENGLISH")){
            valor = 1
        } else {
            valor = 0
        }


        fraseActual = frases.get(i)
        if (fraseActual.get(fraseActual.length-1) == '?' || fraseActual.get(fraseActual.length-1) == '!'){
            fraseActual = (fraseActual.substring(valor, fraseActual.length-1))
            println(fraseActual)
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
            mqttCliente.publish("juego/idioma", "Mensaje: $inputText")
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

    fun aplicardalt(){
        var dalt = settings.getString("daltonismo","OFF")
        if (dalt != null) {
            aplicarTipoDaltonismo(dalt)
        }
    }

    fun aplicarTipoDaltonismo(dalt: String) {

        val protanomalia = floatArrayOf(
            0.567f, 0.433f, 0.0f, 0.0f, 0f,
            0.558f, 0.442f, 0.0f, 0.0f, 0f,
            0.0f, 0.242f, 0.758f, 0.0f, 0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0f
        )

        val deuteronomalia = floatArrayOf(
            0.625f, 0.375f, 0.0f, 0.0f, 0f,
            0.7f, 0.3f, 0.0f, 0.0f, 0f,
            0.0f, 0.3f, 0.7f, 0.0f, 0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0f
        )

        val tritanomalia = floatArrayOf(
            0.95f, 0.05f, 0.0f, 0.0f, 0f,
            0.0f, 0.433f, 0.567f, 0.0f, 0f,
            0.0f, 0.475f, 0.525f, 0.0f, 0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0f
        )

        var matrix = floatArrayOf(
            1f, 0f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
            0f, 0f, 0f, 1f, 0f
        )

        if (dalt.equals("Protanomalia")){
            matrix = protanomalia
        }else if(dalt.equals("Deuteronomalia")){
            matrix = deuteronomalia
        }else if(dalt.equals("Tritanomalia")){
            matrix = tritanomalia
        }

        grabar.background.colorFilter = ColorMatrixColorFilter(matrix)
        nivelTextLengua.background.colorFilter = ColorMatrixColorFilter(matrix)
    }

}
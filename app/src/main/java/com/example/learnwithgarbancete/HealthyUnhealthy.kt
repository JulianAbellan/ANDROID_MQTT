package com.example.learnwithgarbancete

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.ColorMatrixColorFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class HealthyUnhealthy : AppCompatActivity() {

    lateinit var backButton2: Button
    lateinit var flecharoja: ImageView
    lateinit var flechaverde: ImageView
    lateinit var fotocentro: ImageView
    lateinit var garbancete: Button
    lateinit var HealthyText : TextView
    lateinit var textGarbancete : TextView
    lateinit var NumberScore : TextView
    lateinit var list: kotlin.collections.List<Int>
    var hamburguesa = R.drawable.hamburguesa
    var lechuga = R.drawable.lechuga
    var pizza = R.drawable.pizza
    var manzana = R.drawable.manzana
    var zanahoria = R.drawable.zanahoria
    var count: Int = 0
    lateinit var texttospeech: TextToSpeech
    lateinit var settings : SharedPreferences
    lateinit var tipodaltonico : String
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    lateinit var sensorEventListener: SensorEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthy_unhealthy)
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        count = 0
        list = listOf<Int>(zanahoria,hamburguesa,lechuga,pizza,manzana)

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

        backButton2 = findViewById(R.id.backButton3)
        flecharoja = findViewById(R.id.flecharoja)
        flechaverde = findViewById(R.id.flechaverde)
        garbancete = findViewById(R.id.garbancete)
        fotocentro = findViewById(R.id.imageView)
        HealthyText = findViewById(R.id.FoodDescription)
        NumberScore = findViewById(R.id.NumberScore)
        textGarbancete = findViewById(R.id.textGarbancete)

        tipodaltonico = settings.getString("daltonico", "").toString()
        aplicarTipoDaltonismo(tipodaltonico)

        backButton2.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            texttospeech.speak(getText(R.string.back).toString(), TextToSpeech.QUEUE_ADD, null);

            startActivity(intent)
        }

        sensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                val x = sensorEvent.values[0]
                if (x < -5) {
                    onPause()
                    giroDerecha()
                } else if (x > 5) {
                    onPause()
                    giroIzq()
                }
            }
        }
        if (sensor == null) finish()


    }

    fun giroDerecha(){
        if(count != list.size) {
                changeImage()
            }else
            {
                clickGarbancete()
            }
    }


    fun giroIzq(){
        if(count != list.size) {
                changeImage()
            }
            else
            {
                clickGarbancete()
            }
    }


    fun changeImage(){

        var siguenteImage = list.get(count)
        NumberScore.setText("Change " + count)

        if(siguenteImage == hamburguesa){
            HealthyText.setText(R.string.hamburguesa)
        }
        if(siguenteImage == pizza){
            HealthyText.setText(R.string.pizza)
        }
        if(siguenteImage == lechuga){
            HealthyText.setText(R.string.lechuga)
        }
        if(siguenteImage == manzana){
            HealthyText.setText(R.string.manzana)
        }
        if(siguenteImage == zanahoria){
            HealthyText.setText(R.string.zanahoria)
        }
        fotocentro.setImageResource(siguenteImage)
        count++

        clickGarbancete()
    }

    fun start() {
        sensorManager.registerListener(
            sensorEventListener,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun clickGarbancete(){
        garbancete.setOnClickListener(){
            start()
        }
    }

    fun stop() {
        sensorManager.unregisterListener(sensorEventListener)
    }

    override fun onPause() {
        stop()
        super.onPause()
    }

    override fun onResume() {
        start()
        super.onResume()
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

        backButton2 = findViewById(R.id.backButton3)
        flecharoja.colorFilter = ColorMatrixColorFilter(matrix)
        flechaverde.colorFilter = ColorMatrixColorFilter(matrix)
        fotocentro.colorFilter = ColorMatrixColorFilter(matrix)
        garbancete.background.colorFilter = ColorMatrixColorFilter(matrix)
    }

}


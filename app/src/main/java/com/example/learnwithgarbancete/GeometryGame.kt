package com.example.learnwithgarbancete

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import java.util.*


class GeometryGame : AppCompatActivity(){

    lateinit var figura1: ImageView
    lateinit var figura2: ImageView
    lateinit var figura3: ImageView
    var x = arrayOf<Float>(0F,0F,0F)
    var y = arrayOf<Float>(0F,0F,0F)
    var initial_x = arrayOf<Float>(0F,0F,0F)
    var initial_y = arrayOf<Float>(0F,0F,0F)
    var dx : Float = 0F
    var dy : Float = 0F
    lateinit var sombra1: ImageView
    lateinit var sombra2: ImageView
    lateinit var sombra3: ImageView
    lateinit var outputGeo: TextView

    lateinit var levelGeo : TextView
    lateinit var back : Button
    lateinit var settings : SharedPreferences
    lateinit var texttospeech : TextToSpeech
    lateinit var figuras : kotlin.collections.List<Int>
    var figura : Int = -1
    var imagenes = listOf<ImageView>()
    var sombras = listOf<ImageView>()
    var relacion = hashMapOf<Int, Int>()
    var i=0
    var contador=0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geometry_game)

        figura1 = findViewById(R.id.figura1)
        sombra1 = findViewById(R.id.sombra1)
        figura2 = findViewById(R.id.figura2)
        sombra2 = findViewById(R.id.sombra2)
        figura3 = findViewById(R.id.figura3)
        sombra3 = findViewById(R.id.sombra3)
        outputGeo = findViewById(R.id.outputGeo)

        levelGeo = findViewById(R.id.levelGeo)
        back = findViewById(R.id.backboton)

        imagenes = listOf(figura1, figura2, figura3)
        sombras = listOf(sombra1, sombra2, sombra3)

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

        figura1.setOnClickListener(){
            figura = 0
            outputGeo.setText(getString(R.string.empiezaGeo))
        }
        figura2.setOnClickListener(){
            figura = 1
            outputGeo.setText(getString(R.string.empiezaGeo))
        }
        figura3.setOnClickListener(){
            figura = 2
            outputGeo.setText(getString(R.string.empiezaGeo))

        }


        back.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(getText(R.string.back).toString(), TextToSpeech.QUEUE_ADD, null);

            startActivity(intent)
        }

       rutina()

    }

    fun rutina(){
        if(i==0){
            initial_x = arrayOf<Float>(0F, 351F, 688F)
            initial_y = arrayOf<Float>(376F, 376F, 376F)
            outputGeo.setText(getString(R.string.empiezaGeo))
        }
        i++
        terminar()
        ponerNivel(i)
        ponerFiguras()
    }

    fun terminar(){
        if(i>5){
            figura1.isEnabled = false
            figura2.isEnabled = false
            figura3.isEnabled = false
            sombra1.isEnabled = false
            sombra2.isEnabled = false
            sombra3.isEnabled = false

        }
    }
    fun ponerNivel(i:Int){
        levelGeo.setText("${getString(R.string.level_name)} ${i}/5")
    }

    fun ponerFiguras(){
        figuras = listOf(R.drawable.circulo, R.drawable.cuadrado, R.drawable.star, R.drawable.triangulo, R.drawable.luna, R.drawable.sol, R.drawable.rombo).shuffled()
        var df = figuras.subList(0,3).shuffled()

        figura1.setImageResource(figuras[0])
        figura2.setImageResource(figuras[1])
        figura3.setImageResource(figuras[2])
        figura1.visibility = View.VISIBLE
        figura2.visibility = View.VISIBLE
        figura3.visibility = View.VISIBLE

        if (i!=1) {
            figura1.x = initial_x[0]
            figura2.x = initial_x[1]
            figura3.x = initial_x[2]
            figura1.y = initial_y[0]
            figura2.y = initial_y[1]
            figura3.y = initial_y[2]
        }

        sombra1.setImageResource(df[0])
        sombra2.setImageResource(df[1])
        sombra3.setImageResource(df[2])
        sombra1.setColorFilter(Color.BLACK)
        sombra2.setColorFilter(Color.BLACK)
        sombra3.setColorFilter(Color.BLACK)

        for (i in 0..2){
            for (j in 0..2){
                if (figuras[i] == df[j]){
                    relacion.put(i, j)
                }
            }
        }

    }




    var evento_x = 0F
    var evento_y = 0F
    var sombra = 0
    override fun onTouchEvent(evento : MotionEvent): Boolean {
        var action = evento.getAction()
        x = arrayOf<Float>(figura1.x, figura2.x, figura3.x)
        y = arrayOf<Float>(figura1.y, figura2.y, figura3.y)

        if(action == MotionEvent.ACTION_DOWN){


            evento_x= evento.getX()
            evento_y= evento.getY()
        }

        if(action == MotionEvent.ACTION_MOVE){

            if(figura > -1) {

                sombra = relacion.get(figura)!!
                dx = evento.getX() - evento_x
                dy = evento.getY() - evento_y


                imagenes[figura].setX(imagenes[figura].getX() + dx)
                imagenes[figura].setY(imagenes[figura].getY() + dy)


                evento_x = evento.getX()
                evento_y = evento.getY()

                var distancia_x = Math.abs(imagenes[figura].x - sombras[sombra].x)
                var distancia_y = Math.abs(imagenes[figura].y - sombras[sombra].y)


                if (distancia_x <= 10 && distancia_y <= 10) {
                    imagenes[figura].visibility = View.INVISIBLE
                    sombras[sombra].setColorFilter(Color.GREEN)


                    checkFinal()
                }
            }
        }

        return super.onTouchEvent(evento);
    }

    fun checkFinal() {
        if (imagenes[0].visibility.equals(View.INVISIBLE) && imagenes[1].visibility.equals(View.INVISIBLE) && imagenes[2].visibility.equals(View.INVISIBLE)){
            figura=-1
            outputGeo.text = getString(R.string.confGeo)
            rutina()
        }
    }
}
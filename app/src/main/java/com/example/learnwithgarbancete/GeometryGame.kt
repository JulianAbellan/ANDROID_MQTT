package com.example.learnwithgarbancete

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import java.util.*


class GeometryGame : AppCompatActivity(){

    lateinit var figura1: ImageView
    lateinit var figura2: ImageView
    lateinit var figura3: ImageView
    lateinit var rotar1: Button
    lateinit var rotar2: Button
    lateinit var rotar3: Button
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
    var rotar = listOf<Button>()
    var figura : Int = -1
    var imagenes = listOf<ImageView>()
    var sombras = listOf<ImageView>()
    var relacion = hashMapOf<Int, Int>()
    var i=0
    var grados1 = 0F
    var grados2 = 0F
    var grados3 = 0F
    var grados = arrayOf(grados1, grados2, grados3)
    var flag = false
    lateinit var random : Random
    lateinit var constraintLayout : ConstraintLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geometry_game)

        figura1 = findViewById(R.id.figura1)
        sombra1 = findViewById(R.id.sombra1)
        figura2 = findViewById(R.id.figura2)
        sombra2 = findViewById(R.id.sombra2)
        figura3 = findViewById(R.id.figura3)
        sombra3 = findViewById(R.id.sombra3)
        rotar1 = findViewById(R.id.rotar1)
        rotar2 = findViewById(R.id.rotar2)
        rotar3 = findViewById(R.id.rotar3)
        outputGeo = findViewById(R.id.outputGeo)

        levelGeo = findViewById(R.id.levelGeo)
        back = findViewById(R.id.backboton)

        random = Random()

        imagenes = listOf(figura1, figura2, figura3)
        rotar = listOf(rotar1, rotar2, rotar3)
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

        rotar1.setOnClickListener(){
            if(grados1>=360F){
                grados[0]=grados1
                figura1.setRotation(0F)
            } else {
                grados1+=2
                grados[0]=grados1
                figura1.setRotation(grados1)
            }
            println(figura1.getRotation())
        }

        rotar1.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
               // while(true){
                    if(grados1>=360F){
                        grados[0]=grados1
                        figura1.setRotation(0F)
                    } else {
                        grados1+=15
                        grados[0]=grados1
                        figura1.setRotation(grados1)
                    }

                    println(figura1.getRotation())
              //  }
                return false
            }
        })

        rotar2.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                // while(true){
                if(grados2>=360F){
                    grados[1]=grados2
                    figura2.setRotation(0F)
                } else {
                    grados2+=15
                    grados[1]=grados2
                    figura2.setRotation(grados2)
                }

                println(figura2.getRotation())
                //  }
                return false
            }
        })

        rotar3.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                // while(true){
                if(grados3>=360F){
                    grados[2]=grados3
                    figura3.setRotation(0F)
                } else {
                    grados3+=15
                    grados[2]=grados3
                    figura3.setRotation(grados3)
                }

                println(figura1.getRotation())
                //  }
                return false
            }
        })

        rotar2.setOnClickListener(){
            if(grados2>=360F){
                grados[1]=grados2
                figura2.setRotation(0F)
            } else {
                grados2+=2
                grados[1]=grados2
                figura2.setRotation(grados2)
            }

            println(figura2.getRotation())
        }
        rotar3.setOnClickListener(){
            if(grados3>=360F){
                grados[2]=grados3
                figura3.setRotation(0F)
            } else {
                grados3+=2
                grados[2]=grados3
                figura3.setRotation(grados3)
            }

            println(figura3.getRotation())

        }


        back.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            if(settings.getString("tts","NO").equals("SI")) texttospeech.speak(getText(R.string.back).toString(), TextToSpeech.QUEUE_ADD, null);

            startActivity(intent)
        }

        constraintLayout = findViewById(R.id.constraintLayout)
        inicializar()
        rutina()

    }

    fun inicializar(){
        var cs = ConstraintSet()
        cs.clone(constraintLayout)
        cs.connect(R.id.figura1, ConstraintSet.RIGHT, R.id.figura1der, ConstraintSet.RIGHT, 0 )
        cs.connect(R.id.figura1, ConstraintSet.LEFT, R.id.figura1izq, ConstraintSet.LEFT, 0 )
        //cs.connect(R.id.figura1, ConstraintSet.TOP, R.id.figuraArriba, ConstraintSet.TOP, 0 )
        cs.connect(R.id.figura1, ConstraintSet.BOTTOM, R.id.figuraAbajo, ConstraintSet.BOTTOM, 0 )

        cs.connect(R.id.figura2, ConstraintSet.RIGHT, R.id.figura2der, ConstraintSet.RIGHT, 0 )
        cs.connect(R.id.figura2, ConstraintSet.LEFT, R.id.figura1der, ConstraintSet.LEFT, 0 )
       // cs.connect(R.id.figura2, ConstraintSet.TOP, R.id.figuraArriba, ConstraintSet.TOP, 0 )
        cs.connect(R.id.figura2, ConstraintSet.BOTTOM, R.id.figuraAbajo, ConstraintSet.BOTTOM, 0 )

        cs.connect(R.id.figura3, ConstraintSet.RIGHT, R.id.figura3der, ConstraintSet.RIGHT, 0 )
        cs.connect(R.id.figura3, ConstraintSet.LEFT, R.id.figura2der, ConstraintSet.LEFT, 0 )
       // cs.connect(R.id.figura3, ConstraintSet.TOP, R.id.figuraArriba, ConstraintSet.TOP, 0 )
        cs.connect(R.id.figura3, ConstraintSet.BOTTOM, R.id.figuraAbajo, ConstraintSet.BOTTOM, 0 )
        cs.applyTo(constraintLayout)
    }

    fun rutina(){
        if(i==0){
            initial_x = arrayOf<Float>(0F, 351F, 688F)
            initial_y = arrayOf<Float>(376F, 376F, 376F)
            outputGeo.setText(getString(R.string.empiezaGeo))
        }
        i++
        inicializar()
        terminar()
        ponerNivel(i)
        ponerFiguras()
    }

    fun terminar(){
        if(i>3){
            figura1.isEnabled = false
            figura2.isEnabled = false
            figura3.isEnabled = false
            sombra1.isEnabled = false
            sombra2.isEnabled = false
            sombra3.isEnabled = false

            outputGeo.setText(getString(R.string.congrats))
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
        rotar1.visibility = View.VISIBLE
        rotar2.visibility = View.VISIBLE
        rotar3.visibility = View.VISIBLE

        /*
        figura1.rotation = random.nextInt(360) + 0.0F
        figura2.rotation = random.nextInt(360) + 0.0F
        figura3.rotation = random.nextInt(360) + 0.0F
        */

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
                rotar[figura].setX(rotar[figura].getX() + dx)
                rotar[figura].setY(rotar[figura].getY() + dy)


                evento_x = evento.getX()
                evento_y = evento.getY()

                var distancia_x = Math.abs(imagenes[figura].x - sombras[sombra].x)
                var distancia_y = Math.abs(imagenes[figura].y - sombras[sombra].y)


                if (distancia_x <= 10 && distancia_y <= 10) {
                    var element = figuras[figura]
                    when (element){
                            R.drawable.circulo -> {
                                flag = true
                            }
                            R.drawable.cuadrado -> {
                                if ((grados[figura] in 0F..5F) || grados[figura] in 85F..95F || grados[figura] in 175F..185F || grados[figura] in 265F..275f || grados[figura] in 355f.. 360F){
                                    flag = true
                                }
                            }
                            R.drawable.star -> {
                                println(grados[figura])
                                if ((grados[figura] in 0F..5F)  || grados[figura] in 67f.. 77F || grados[figura] in 139f..149F || grados[figura] in 211F..221f || grados[figura] in 283F..293f || grados[figura] in 355f.. 360F){
                                    flag = true
                                }
                            }
                            R.drawable.triangulo -> {
                                if ((grados[figura] in 0F..5F)  || grados[figura] in 115F..125f || grados[figura] in  235F..245f || grados[figura] in 355f.. 360F){
                                    flag = true
                                }
                            }
                            R.drawable.luna -> {
                                if ((grados[figura] in 0F..5F)  || grados[figura] in 355f.. 360F){
                                    flag=true
                                }
                            }
                            R.drawable.sol -> {
                                if ((grados[figura] in 0F..5F)  || grados[figura] in 31F..41f || grados[figura] in  67F..77f || grados[figura] in 103F..113f || grados[figura] in 139F..149f || grados[figura] in 175F..185f || grados[figura] in 211F..221f || grados[figura] in 247F..257f || grados[figura] in 283F..293f || grados[figura] in 319F..329f || grados[figura] in 355f.. 360F ){
                                    flag = true
                                }
                            }
                            R.drawable.rombo -> {
                                if ((grados[figura] in 0F..5F)  || grados[figura] in 175F..185F || grados[figura] in 355f.. 360F){
                                    flag = true
                                }
                            }

                        }


                    if (flag) {
                        println("awanabunbambamwachuwario")
                        flag = false
                        imagenes[figura].visibility = View.INVISIBLE
                        rotar[figura].visibility = View.INVISIBLE
                        sombras[sombra].setColorFilter(Color.GREEN)


                        checkFinal()
                    }
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
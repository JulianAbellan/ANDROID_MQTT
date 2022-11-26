package com.example.learnwithgarbancete

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.*
import android.content.pm.*
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.accessibility.AccessibilityManager
import android.widget.*
import java.io.*
import java.util.*


class ConfigurationActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var leftLanguage: Button
    lateinit var rightLanguage: Button
    lateinit var leftDalt: Button
    lateinit var rightDalt: Button
    lateinit var language: TextView
    lateinit var settings: SharedPreferences
    lateinit var idioma: String
    lateinit var colorblind: TextView
    lateinit var daltonico: String
    lateinit var vpd: Button
    lateinit var edit: SharedPreferences.Editor
    lateinit var garbancete: ImageView
    lateinit var switchVisual: Switch
    lateinit var textGarbancete: TextView
    lateinit var texttospeech: TextToSpeech
    lateinit var auxText: String
    lateinit var RestoreDefaultSettingsBtn: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration2)

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

        button = findViewById(R.id.buttonBack)
        vpd = findViewById(R.id.RestoreDefaultSettingsBtn)
        leftLanguage = findViewById(R.id.leftLanguageMode)
        rightLanguage = findViewById(R.id.rightLanguageMode)
        leftDalt = findViewById(R.id.leftDaltMode)
        rightDalt = findViewById(R.id.rightDaltMode)
        language = findViewById(R.id.languageSelect)
        colorblind = findViewById(R.id.colorBlindnessMode)
        garbancete = findViewById(R.id.garbanConf)
        switchVisual = findViewById(R.id.switchVisual)
        textGarbancete =  findViewById(R.id.textView)
        RestoreDefaultSettingsBtn =  findViewById(R.id.RestoreDefaultSettingsBtn)


        cargarpreferencias()
        edit = settings.edit()

        button.setOnClickListener() {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        RestoreDefaultSettingsBtn.setOnClickListener() {
            textGarbancete.setText(getText(R.string.restoreValues))
            texttospeech.speak(getText(R.string.restoreValues).toString(), TextToSpeech.QUEUE_ADD, null);

        }

        leftLanguage.setOnClickListener() {
            setLanguage()
        }

        rightLanguage.setOnClickListener() {
            setLanguage()
        }

        rightDalt.setOnClickListener() {
            swapDalt()
        }

        leftDalt.setOnClickListener() {
            swapDalt()
        }

        vpd.setOnClickListener() {
            establecerValoresPorDefecto()
        }

        garbancete.setOnClickListener() {
            activarTutorial()
        }

        switchVisual.setOnCheckedChangeListener(){_, isCheked ->
            if(isCheked){
                textGarbancete.setText(getText(R.string.activaAsist))
                auxText = textGarbancete.text.toString()
                texttospeech.speak(auxText, TextToSpeech.QUEUE_ADD, null);
            }else{
                textGarbancete.setText(getText(R.string.desactivaAsist))
                auxText = textGarbancete.text.toString()
                texttospeech.speak(auxText, TextToSpeech.QUEUE_ADD, null);
            }
        }

        checkTutorial()
    }

    fun enableTalkBack() {
        try {
            val am =
                applicationContext.getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
            val services = am.installedAccessibilityServiceList
            if (services.isEmpty()) {
                return
            }
            var service = services[0]
            var enableTouchExploration = (service.flags
                    and AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE) != 0
            // Try to find a service supporting explore by touch.
            if (!enableTouchExploration) {
                val serviceCount = services.size
                for (i in 1 until serviceCount) {
                    val candidate = services[i]
                    if (candidate.flags and AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE != 0) {
                        enableTouchExploration = true
                        service = candidate
                        break
                    }
                }
            }
            val serviceInfo: ServiceInfo = service.resolveInfo.serviceInfo
            val componentName = ComponentName(serviceInfo.packageName, serviceInfo.name)
            val enabledServiceString: String = componentName.flattenToString()
            val resolver: ContentResolver = applicationContext.getContentResolver()
            Settings.Secure.putString(
                resolver,
                "enabled_accessibility_services",
                enabledServiceString
            )
            Settings.Secure.putString(
                resolver,
                "touch_exploration_granted_accessibility_services",
                enabledServiceString
            )
            if (enableTouchExploration) {
                Settings.Secure.putInt(resolver, "touch_exploration_enabled", 1)
            }
            Settings.Secure.putInt(resolver, "accessibility_script_injection", 1)
            Settings.Secure.putInt(resolver, "accessibility_enabled", 1)
        } catch (e: Exception) {
            Log.e("Device", "Failed to enable accessibility: $e")
        }
    }

    fun checkTutorial() {
        var tuto = settings.getString("tutorial", "si").toString()
        println(tuto)
        if (tuto.equals("si")) {
            activarTutorial()
        }
    }

    fun activarTutorial() {
        edit.putString("tutorial", "no")
        edit.commit()

        val intent: Intent = Intent(this, Tutorial::class.java)
        startActivity(intent)
    }

    fun swapDalt() {
        if (colorblind.getText().equals("ON")) {
            cargarDaltonico("OFF")
            guardarpreferenciasDalt("OFF")
        } else if (colorblind.getText().equals("OFF")) {
            cargarDaltonico("ON")
            guardarpreferenciasDalt("ON")
        }
    }

    fun cargarpreferencias() {
        settings = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        idioma = settings.getString("idioma", "ENGLISH").toString()
        chooseLanguage(idioma)

        daltonico = settings.getString("daltonico", "OFF").toString()
        cargarDaltonico(daltonico)
    }

    fun cargarDaltonico(daltonico: String) {
        if (daltonico.equals("ON")) {
            colorblind.setText("ON")
            textGarbancete.setText(getText(R.string.activaColor).toString())
            texttospeech.speak(getText(R.string.activaColor).toString(), TextToSpeech.QUEUE_ADD, null);
        } else {
            colorblind.setText("OFF")
            textGarbancete.setText(getText(R.string.desactivaColor).toString())
            texttospeech.speak(getText(R.string.desactivaColor).toString(), TextToSpeech.QUEUE_ADD, null);
        }
    }

    fun cargarpreferencias2(): String {

        idioma = settings.getString("idioma", "").toString()
        return idioma
    }

    fun establecerValoresPorDefecto() {
        chooseLanguage("ENGLISH")
    }

    fun guardarpreferenciasIdioma(l: String) {
        var edit = settings.edit()
        edit.putString("idioma", l)
        edit.commit()
        edit.apply()

    }

    fun guardarpreferenciasDalt(l: String) {
        var edit = settings.edit()
        edit.putString("daltonico", l)
        edit.commit()
        edit.apply()

    }


    fun setLanguage() {
        val l = language.getText()

        if (l.equals("ESPAÑOL")) {
            chooseLanguage("ENGLISH")
            texttospeech.setLanguage(Locale.ENGLISH)
            textGarbancete.setText(getText(R.string.ChangeEnglish).toString())
            texttospeech.speak(getText(R.string.ChangeEnglish).toString(), TextToSpeech.QUEUE_ADD, null);

        } else if (l.equals("ENGLISH")) {
            chooseLanguage("ESPAÑOL")
            val locSpanish = Locale("spa", "ES")

            texttospeech.setLanguage(locSpanish)
            textGarbancete.setText(getText(R.string.ChangeSpanish).toString())
            texttospeech.speak(getText(R.string.ChangeSpanish).toString(), TextToSpeech.QUEUE_ADD, null);

        } else {
            language.setText(cargarpreferencias2())
            cargarpreferencias()
        }

        val intent: Intent = Intent(this, Auxiliar::class.java)
        startActivity(intent)
    }

    fun chooseLanguage(l: String) {
        var lang = ""
        var country = ""

        if (l.equals("ESPAÑOL")) {
            lang = "es"
            country = "ES"


        } else if (l.equals("ENGLISH")) {
            lang = "en"
            country = "EN"
        }


        val localizacion = Locale(lang, country)

        Locale.setDefault(localizacion)
        val config = Configuration()
        config.locale = localizacion
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )

        guardarpreferenciasIdioma(l)
        language.setText(l)
    }
}




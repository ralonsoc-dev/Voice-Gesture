package com.example.voicegesture

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.voicegesture.model.TextToVoice

class TranslateActivity : AppCompatActivity() {

    lateinit var textToVoice: TextToVoice
    lateinit var btnTranslate: Button
    lateinit var btnSettings: Button
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    var contador: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_translate)

        // Instancio clase text to voice
        textToVoice = TextToVoice(this)
        textToVoice.initTTS()

        // Enlazo botones de la vista
        btnTranslate = findViewById(R.id.btnTranslete1)
        btnSettings = findViewById(R.id.btnReturn1)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)

        // Se establecen escuchadores de click para los botones
        btnTranslate.setOnClickListener(clickListener)
        btnSettings.setOnClickListener(clickListener)
        btn1.setOnClickListener(clickListener)
        btn2.setOnClickListener(clickListener)
        btn3.setOnClickListener(clickListener)
        btn4.setOnClickListener(clickListener)
        btn5.setOnClickListener(clickListener)
    }

    /**
     * Metodo escuchador para los botones
     */
    private val clickListener = View.OnClickListener { view ->
        when (view.id){
            R.id.btnTranslete1 -> {
                traducir()
            }
            R.id.btnReturn1 -> {
                finish()
            }
            R.id.btn1 -> {
                textToVoice.translate("Hola")
            }
            R.id.btn2 -> {
                textToVoice.translate("Buenas tardes")
            }
            R.id.btn3 -> {
                textToVoice.translate("Gracias")
            }
            R.id.btn4 -> {
                textToVoice.translate("Preguntar")
            }
            R.id.btn5 -> {
                textToVoice.translate("Lengua de signos")
            }
        }
    }

    private fun traducir() {

        val data = listOf(
            "Hola",
            "Buenas tardes",
            "Gracias",
            "Preguntar",
            "Lengua de signos"
        )

        textToVoice.translate(data.get(contador))

        contador += 1
        if (contador == 5)
            contador = 0
    }
}
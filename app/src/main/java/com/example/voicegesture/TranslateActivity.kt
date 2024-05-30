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

        // Se establecen escuchadores de click para los botones
        btnTranslate.setOnClickListener(clickListener)
        btnSettings.setOnClickListener(clickListener)
    }

    /**
     * Metodo escuchador para los botones
     */
    private val clickListener = View.OnClickListener { view ->
        when (view.id){
            R.id.btnTranslete1 -> {
                textToVoice.translate("Comienzo o que lo que loco, yo no se pero tu que")
            }
            R.id.btnReturn1 -> {
                finish()
            }
        }
    }
}
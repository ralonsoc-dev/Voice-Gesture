package com.example.voicegesture

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.voicegesture.model.FourierTransformer
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

                val data = listOf(
                    doubleArrayOf(11.0, -6.0, 44.0, 9.0, 4.0, -5.38, -4.42, 6.36, 35.39, 1.42, -0.37),
                    doubleArrayOf(25.0, 37.0, 35.0, 1.0, -2.0, -5.39, -4.33, 6.38, 35.02, 1.43, -0.48),
                    doubleArrayOf(13.0, 54.0, 45.0, 16.0, 12.0, -5.40, -4.33, 6.39, 34.40, 1.30, -0.60),
                    doubleArrayOf(20.0, 2.0, 0.0, 19.0, 5.0, -5.28, -4.33, 6.35, 35.11, 1.55, -0.50),
                    doubleArrayOf(8.0, -5.0, 30.0, 23.0, 28.0, -5.38, -4.40, 6.33, 34.97, 1.60, -0.48)
                )

                // Crear una instancia de FourierTransformer
                val transformer = FourierTransformer(data)

                // Calcular la DFT para la matriz de datos
                val dftResult = transformer.dft()

                Log.d("Rata", "-------------------------------------------------")
                // Imprimir los resultados
                dftResult.forEach { row ->
                    row.forEach { println(it) }
                    println()
                }
                Log.d("Rata", "-------------------------------------------------")
            }
            R.id.btnReturn1 -> {
                finish()
            }
        }
    }
}
package com.example.voicegesture

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var btnTranslate: Button
    lateinit var btnSettings: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Enlazo botones de la vista
        btnTranslate = findViewById(R.id.btnTranslate)
        btnSettings = findViewById(R.id.btnSettings)

        // Se establecen escuchadores de click para los botones
        btnTranslate.setOnClickListener(clickListener)
        btnSettings.setOnClickListener(clickListener)
    }

    /**
     * Metodo escuchador para los botones
     */
    private val clickListener = View.OnClickListener { view ->
        when (view.id){
            R.id.btnTranslate -> {
                val translateActivity = Intent(this, TranslateActivity::class.java)
                startActivity(translateActivity)
            }
            R.id.btnSettings -> {
                val settingsActivity = Intent(this, SettingsActivity::class.java)
                startActivity(settingsActivity)
            }
        }
    }


    /**
     * Mostrar msg toast
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
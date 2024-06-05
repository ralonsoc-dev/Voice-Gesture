package com.example.voicegesture.model

import android.content.Context
import android.content.SharedPreferences
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class TextToVoice(private val context: Context) {

    lateinit var tts: TextToSpeech
    lateinit var lenguaje: Locale
    private var translator: Translator = Translator()
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("TTS_PREFERENCES", Context.MODE_PRIVATE)

    companion object {
        const val KEY_SELECTED_LANGUAGE = "selected_language"
    }

    /**
     * Iniciar clase
     */
    fun initTTS() {
        val selectedLanguage = getLanguage()

        tts = TextToSpeech(context, TextToSpeech.OnInitListener {
            if (it==TextToSpeech.SUCCESS) {
                if (null != selectedLanguage && selectedLanguage != "") {
                    if (selectedLanguage == "en")
                        tts.setLanguage(Locale(selectedLanguage, "GB"));
                    else
                        tts.setLanguage(Locale(selectedLanguage, selectedLanguage.toUpperCase()));
                }
                else
                    tts.setLanguage(Locale("es", "ES"));

                tts.setSpeechRate(0.8f)
                tts.voices
            }
        })
    }

    /**
     * Se encarga de convertir el texto en voz
     * @param text Texto a traducir y hablar
     */
    fun translate(text: String){
        var translatedText: String = ""
        GlobalScope.launch(Dispatchers.Main) {
            try {
                var to = getLanguage()?: "es"
                translatedText = translator.translateText("es", to, text)
                delay(300)
                tts.speak("${translatedText}", TextToSpeech.QUEUE_ADD, null)
            } catch (e: Exception) {
                Log.e("TRADUCIR", "Error al traducir texto: ${e.message}")
            }
        }
    }

    /**
     * Coger lenguaje guardado en sharedPreferences
     */
    fun getLanguage(): String? {
        return sharedPreferences.getString(KEY_SELECTED_LANGUAGE, "es");
    }

    /**
     * Settear lenguaje en sharedPreferences
     * @param language Lenguaje seleccionado
     */
    fun setLanguage(language: String) {
        var key: String = ""
        when (language) {
            "Español" -> {
                key = "es"
                tts.setLanguage(Locale(key, key.toUpperCase()));
                lenguaje = Locale(key, key.toUpperCase())
            }
            "Ingles" -> {
                key = "en"
                tts.setLanguage(Locale(key, "GB"));
                lenguaje = Locale(key, "GB")
            }
            "Frances" -> {
                key = "fr"
                tts.setLanguage(Locale(key, key.toUpperCase()))
                lenguaje = Locale(key, key.toUpperCase())
            }
            "Italiano" -> {
                key = "it"
                tts.setLanguage(Locale(key, key.toUpperCase()))
                lenguaje = Locale(key, key.toUpperCase())
            }
            "Portugues" -> {
                key = "pt"
                tts.setLanguage(Locale(key, key.toUpperCase()));
                lenguaje = Locale(key, key.toUpperCase())
            }
        }
        if (key != "") {
            with(sharedPreferences.edit()) {
                putString(KEY_SELECTED_LANGUAGE, key)
                apply()
            }
        }
    }
}
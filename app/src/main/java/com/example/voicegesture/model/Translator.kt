package com.example.voicegesture.model

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Translator {

    lateinit var translatorOptions: TranslatorOptions
    lateinit var translator: Translator

    /**
     * Traduce texto de un idioma a otro idioma indicado
     * @param from Idioma en el que esta
     * @param to Idioma al que traducir
     * @param text Texto a traducir
     */
    suspend fun translateText(from: String, to: String, text: String): String {
        return suspendCancellableCoroutine { continuation ->
            // Configuración de opciones del traductor
            translatorOptions = TranslatorOptions.Builder()
                .setSourceLanguage(from)
                .setTargetLanguage(to)
                .build()
            translator = Translation.getClient(translatorOptions)

            // Para descargar el modelo de traducción
            val downloadConditions = DownloadConditions.Builder()
                .requireWifi()
                .build()

            // Descarga el modelo de traduccion si es necesario
            translator.downloadModelIfNeeded(downloadConditions)
                .addOnSuccessListener {
                    translator.translate(text)
                        .addOnSuccessListener { translatedText ->
                            continuation.resume(translatedText)
                        }
                        .addOnFailureListener { e ->
                            Log.d("TRADUCIR", "Error al traducir: $e")
                            continuation.resumeWithException(e)
                        }
                }
                .addOnFailureListener { e ->
                    Log.d("TRADUCIR", "Error al descargar el modelo: $e")
                    continuation.resumeWithException(e)
                }
        }
    }
}
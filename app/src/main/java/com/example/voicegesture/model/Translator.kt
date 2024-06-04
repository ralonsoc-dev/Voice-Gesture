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
        Log.d("TRADUCIR", "text1 ${text}")
        return suspendCancellableCoroutine { continuation ->
            translatorOptions = TranslatorOptions.Builder()
                .setSourceLanguage(from)
                .setTargetLanguage(to)
                .build()

            Log.d("TRADUCIR", "text2 ${text}")
            translator = Translation.getClient(translatorOptions)

            Log.d("TRADUCIR", "text3 ${text}")
            val downloadConditions = DownloadConditions.Builder()
                .requireWifi()
                .build()

            translator.downloadModelIfNeeded(downloadConditions)
                .addOnSuccessListener {
                    translator.translate(text)
                        .addOnSuccessListener { translatedText ->
                            Log.d("TRADUCIR", "text4 ${text} text4 ${translatedText}")
                            continuation.resume(translatedText)
                        }
                        .addOnFailureListener { e ->
                            Log.d("TRADUCIR", "ERROR 1 $e")
                            continuation.resumeWithException(e)
                        }
                }
                .addOnFailureListener { e ->
                    Log.d("TRADUCIR", "ERROR $e")
                    continuation.resumeWithException(e)
                }
        }
    }
}
package com.example.voicegesture.model

import android.util.Log
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.pow

class Translator {
    lateinit var translatorOptions: TranslatorOptions
    lateinit var translator: Translator

    suspend fun translateText(from: String, to: String, text: String): String {
        return suspendCancellableCoroutine { continuation ->
            val translatorOptions = TranslatorOptions.Builder()
                .setSourceLanguage(from)
                .setTargetLanguage(to)
                .build()

            val translator = Translation.getClient(translatorOptions)

            val downloadConditions = DownloadConditions.Builder()
                .requireWifi()
                .build()

            translator.downloadModelIfNeeded(downloadConditions)
                .addOnSuccessListener {
                    translator.translate(text)
                        .addOnSuccessListener { translatedText ->
                            Log.d("TRADUCIR", "Traducido1 $translatedText")
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
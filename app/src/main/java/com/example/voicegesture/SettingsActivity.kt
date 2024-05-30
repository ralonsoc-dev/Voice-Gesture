package com.example.voicegesture

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.voicegesture.model.Language
import com.example.voicegesture.model.Languages
import com.example.voicegesture.model.LanguagesArrayAdapter
import com.example.voicegesture.model.People
import com.example.voicegesture.model.PeopleArrayAdapter
import com.example.voicegesture.model.Person
import com.example.voicegesture.model.TextToVoice

class SettingsActivity : AppCompatActivity() {

    lateinit var  textToVoice: TextToVoice
    lateinit var btnSave: Button
    lateinit var btnReturn: Button
    lateinit var spinnerVoz: Spinner
    lateinit var spinnerIdioma: Spinner

    private var selectedLanguage: Language? = null
    private var selectedPerson: Person? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.settings_activity)

        // Instancio la clase que traduce texto a voz para su configuracion
        textToVoice = TextToVoice(this)
        textToVoice.initTTS()

        // Enlazo botones de la vista
        btnSave = findViewById(R.id.btnSave)
        btnReturn = findViewById(R.id.btnReturn)

        // Se establecen escuchadores de click para los botones
        btnSave.setOnClickListener(clickListener)
        btnReturn.setOnClickListener(clickListener)

        // Enlazo los spinner de la vista
        spinnerVoz = findViewById(R.id.spinnerVoz)
        spinnerIdioma = findViewById(R.id.spinnerIdioma)

        initSpinner()
    }

    /**
     * Cargo los spinner
     */
    fun initSpinner() {
        // Relleno el spiner de voz
        configSpinnerVoz()

        // Relleno el spiner de idiomas
        configSpinnerIdioma()
    }

    /**
     * Configuracion del Spinner Voz
     */
    private fun configSpinnerVoz() {
        val voiceList = listOf("Mujer", "Hombre")
        val voiceAdapter = PeopleArrayAdapter(this, People.list!!)
        voiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerVoz.adapter = voiceAdapter

        spinnerVoz.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPerson = parent?.getItemAtPosition(position) as Person
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar el caso en el que no se selecciona nada
            }
        }
    }

    /**
     * Configuracion del Spinner Idioma
     */
    private fun configSpinnerIdioma() {
        val languagesAdapter = LanguagesArrayAdapter(this, Languages.list!!)
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIdioma.adapter = languagesAdapter

        spinnerIdioma.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedLanguage = parent?.getItemAtPosition(position) as Language
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar el caso en el que no se selecciona nada
            }
        }
    }

    /**
     * Metodo escuchador para los botones
     */
    private val clickListener = View.OnClickListener { view ->
        when (view.id){
            R.id.btnSave -> {
                save()
            }
            R.id.btnReturn -> {
                finish()
            }
        }
    }

    /**
     * Guardar la configuración de voz e idioma
     */
    fun save() {
        if (null != selectedLanguage && null != selectedPerson) {
            textToVoice.setLanguage(selectedLanguage!!.name)
            textToVoice.setVoice(selectedPerson!!.name)
        }
    }

    /**
     * Mostrar msg toast
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
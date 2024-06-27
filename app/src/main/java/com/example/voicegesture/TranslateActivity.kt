package com.example.voicegesture

import ReadBluetooth
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.voicegesture.model.FourierTransformer
import com.example.voicegesture.model.TextToVoice
import com.google.android.material.snackbar.Snackbar
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.widget.Toast
import java.io.IOException
import java.io.InputStream
import java.util.UUID

class TranslateActivity : AppCompatActivity() {

    lateinit var textToVoice: TextToVoice

    lateinit var btnTranslate: Button
    lateinit var btnReturn: Button
    lateinit var btnReadLeter: Button
    lateinit var btnDeleteLeter: Button
    lateinit var btnDeletePalabra: Button

    lateinit var textBox: TextView

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private var bluetoothDevice: BluetoothDevice? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var inputStream: InputStream? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_translate)

        // Instancio clase text to voice
        textToVoice = TextToVoice(this)
        textToVoice.initTTS()

        // Enlazo textView
        textBox = findViewById(R.id.textBox)

        // Enlazo botones de la vista
        btnTranslate = findViewById(R.id.btnTranslate1)
        btnReturn = findViewById(R.id.btnReturn1)
        btnReadLeter = findViewById(R.id.btnTranslateLetra)
        btnDeleteLeter = findViewById(R.id.btnDeleteLetra)
        btnDeletePalabra = findViewById(R.id.btnDeletePalabra)


        // Se establecen escuchadores de click para los botones
        btnTranslate.setOnClickListener(clickListener)
        btnReturn.setOnClickListener(clickListener)
        btnReadLeter.setOnClickListener(clickListener)
        btnDeleteLeter.setOnClickListener(clickListener)
        btnDeletePalabra.setOnClickListener(clickListener)

        // Aquí configura tu dispositivo Bluetooth según su dirección MAC
        val bluetoothDeviceAddress = "00:00:00:00:00:00" // Dirección MAC de tu dispositivo
        bluetoothDevice = bluetoothAdapter?.getRemoteDevice(bluetoothDeviceAddress)


        // Reviso permisos y si no los tengo los solicito
        if (!hasPermissions()) {
            Log.d("PERMISOS", "Solicitando permisos..")
            // Solicitamos los permisos necesarios con lo siquiente
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_CONNECT), REQUEST_ALL_PERMISSIONS)
            Log.e("PERMISOS", "Respuesta obtenida: ${hasPermissions()}")
        } else {
            Log.d("PERMISOS", "Permisos ya otorgados")
            setupBluetooth()
            // Conectar al dispositivo Bluetooth
        }
    }

    /**
     * Una vez respondido la solicitud de permisos actua si queremos
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_ALL_PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Log.e("PERMISOS", "Los permisos fueron otorgados")
            } else {
                // Algunos permisos no fueron concedidos, manejar esto según tus requerimientos
                Log.e("PERMISOS", "Error al conceder los permisos")
                showToast("Faltan permisos que otorgar..")
            }
        }
    }

    /**
     * Comprueba si tenemos los permisos necesarios
     */
    private fun hasPermissions(): Boolean {
        Log.e("PERMISOS", "${ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED}")
        return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Metodo escuchador para los botones
     */
    private val clickListener = View.OnClickListener { view ->
        when (view.id){
            R.id.btnTranslate1 -> {
                traducir()
            }
            R.id.btnReturn1 -> {
                finish()
            }
            R.id.btnTranslateLetra -> {
//                LEER 1 LETRA DEL TEXT BOX
                readGlove()
            }
            R.id.btnDeleteLetra -> {
//                BORRAR 1 LETRA DEL TEXT BOX
                deleteLastCharacter()
            }
            R.id.btnDeletePalabra -> {
//                BORRAR PALABRA
                textBox.text = ""
            }
        }
    }

    private fun traducir() {
//        bluetoothManager = ReadBluetooth(this, "Tu Gesto es mi Voz")
//        if (bluetoothManager.connectToDevice()) {
//            var dato = bluetoothManager.readData()
//            val datos: List<Double> = dato.split(",").map { it.toDouble() }
//            var fourierTransformer = FourierTransformer(datos)
//        } else {
//            Log.d("TRADUCIR", "ERROR")
//        }

        val text = textBox.text.toString()
        if (text.isNotEmpty()) {
            textToVoice.translate(textBox.text.toString())
        }
        else {
            Log.d("TRADUCIR", "No hay palabra que traducir ")
        }

    }

    private fun readGlove() {


        addCharacter('s')
    }
    private fun addCharacter(char: Char) {
        val text = textBox.text.toString()
        textBox.setText("$text$char")
    }

    private fun deleteLastCharacter() {
        val text = textBox.text.toString()
        if (text.isNotEmpty()) {
            textBox.setText(text.substring(0, text.length - 1))
        }
    }

    companion object {
        private const val REQUEST_ALL_PERMISSIONS = 1
    }

    /**
     * Mostrar msg toast
     * @param message Mensaje a mostrar en el Toast
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private fun setupBluetooth() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter == null) {
            Log.e("Bluetooth", "El dispositivo no soporta Bluetooth")
            return
        }

        val deviceAddress = "00:00:00:00:00:00" // Reemplaza con la dirección MAC de tu dispositivo Bluetooth

        val bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress)

        // UUID para el servicio Bluetooth (ejemplo para SPP)
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

        try {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    REQUEST_ALL_PERMISSIONS
                )
                Log.d("Bluetooth", "Conectado a ${ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED}")

                return
            }
            val socket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid)
            socket.connect()
            Log.d("Bluetooth", "Conectado a ${bluetoothDevice.name}")

            // Lógica para leer datos del socket
            readDataFromSocket(socket)

        } catch (e: IOException) {
            Log.e("Bluetooth", "Error al conectar", e)
        }
    }

    private fun readDataFromSocket(socket: BluetoothSocket) {
        try {
            val inputStream = socket.inputStream
            val buffer = ByteArray(1024)
            var bytesRead: Int

            // Escucha continuamente los datos del inputStream
            while (true) {
                bytesRead = inputStream.read(buffer)
                val receivedMessage = String(buffer, 0, bytesRead)

                // Procesa los datos recibidos según tu aplicación
                Log.d("Bluetooth", "Datos recibidos: $receivedMessage")

                // Ejemplo de cómo manejar los datos recibidos
                runOnUiThread {
                    // Actualizar la interfaz de usuario con los datos recibidos
                    // Por ejemplo, mostrarlos en un TextView
                    textBox.append(receivedMessage)
                }
            }

        } catch (e: IOException) {
            Log.e("Bluetooth", "Error al leer datos del socket", e)
        }
    }

    private fun closeBluetoothConnection() {
        try {
            inputStream?.close()
            bluetoothSocket?.close()
        } catch (e: IOException) {
            Log.e("Bluetooth", "Error al cerrar la conexión: ${e.message}")
        }
    }
}
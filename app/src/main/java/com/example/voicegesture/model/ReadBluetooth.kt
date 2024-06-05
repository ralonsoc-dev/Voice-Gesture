import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import java.io.IOException
import java.io.InputStream
import java.util.UUID

class ReadBluetooth(private val context: Context, private val deviceName: String) {
    private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // UUID para conexión Bluetooth SPP
    private lateinit var bluetoothSocket: BluetoothSocket
    private lateinit var inputStream: InputStream

    fun connectToDevice(): Boolean {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val device =
            if (checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT)
                != PermissionChecker.PERMISSION_GRANTED
            ) {
                // Manejar falta de permisos
                null
            } else {
                bluetoothAdapter.bondedDevices.firstOrNull { it.name == deviceName }
            }

        if (device != null) {
            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID)
                bluetoothSocket.connect()
                inputStream = bluetoothSocket.inputStream
                return true
            } catch (e: IOException) {
                // Manejar errores de conexión
                return false
            }
        }
        return false
    }

    fun readData(): String {
        val buffer = ByteArray(1024)
        val bytes: Int

        try {
            bytes = inputStream.read(buffer)
            return String(buffer, 0, bytes)
        } catch (e: IOException) {
            // Manejar errores de lectura
            return ""
        }
    }

    fun closeConnection() {
        try {
            bluetoothSocket.close()
        } catch (e: IOException) {
            // Manejar errores de cierre de conexión
        }
    }
}
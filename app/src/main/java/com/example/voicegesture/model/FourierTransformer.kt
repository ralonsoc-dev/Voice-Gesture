package com.example.voicegesture.model

//import org.openrndr.extra.fft.FFT
//import org.openrndr.extra.fft.fft
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class FourierTransformer(private val data: List<Double>) {

    /**
     * Calcula la Transformada Discreta de Fourier (DFT) para la lista de datos
     */
    fun dft(): List<Complex> {
        val n = data.size
        val result = mutableListOf<Complex>()

        for (k in 0 until n) {
            var re = 0.0
            var im = 0.0
            for (t in 0 until n) {
                val angle = 2 * PI * k * t / n
                re += data[t] * cos(angle)
                im -= data[t] * sin(angle)
            }
            result.add(Complex(re, im))
        }

        return result
    }

    data class Complex(val real: Double, val imaginary: Double) {
        override fun toString(): String {
            return "(${real.format()}${if (imaginary >= 0) "+" else ""}${imaginary.format()}j)"
        }

        private fun Double.format(): String {
            return String.format("%.15f", this)
        }
    }
}
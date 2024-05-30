package com.example.voicegesture.model

//import org.openrndr.extra.fft.FFT
//import org.openrndr.extra.fft.fft
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class FourierTransformer(private val data: List<DoubleArray>) {

//    fun performFFT(wave: DoubleArray): DoubleArray {
//        return FFT.fft(wave)
//    }


    /**
     * Calcula la Transformada Discreta de Fourier (DFT) para una fila de datos
     * @param x
     */
    private fun dftRow(x: DoubleArray): Array<Complex> {
        val n = x.size
        val result = Array(n) { Complex(0.0, 0.0) }

        for (k in 0 until n) {
            var re = 0.0
            var im = 0.0
            for (t in 0 until n) {
                val angle = 2 * PI * k * t / n
                re += x[t] * cos(angle)
                im -= x[t] * sin(angle)
            }
            result[k] = Complex(re, im)
        }

        return result
    }

    /**
     * Calcula la Transformada de Fourier Discreta (DFT) para la matriz de datos
     */
    fun dft(): List<Array<Complex>> {
        return data.map { dftRow(it) }
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
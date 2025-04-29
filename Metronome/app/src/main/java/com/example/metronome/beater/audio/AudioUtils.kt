package com.example.metronome.beater.audio

import java.io.InputStream
import kotlin.math.sin

/**
 * Egy objektum segédfüggvényekkel a hangkezeléshez, beleértve a WAV fájlok feldolgozását
 * és PCM adatok generálását.
 */
object AudioUtils {

    /**
     * WAV fájl beolvasása egy bemeneti adatfolyamból.
     *
     * @param stream Az `InputStream`, amely a WAV fájlt tartalmazza.
     * @return A WAV fájl adatai `ByteArray` formátumban. Hiba esetén üres tömböt ad vissza.
     */
    fun readWav(stream: InputStream): ByteArray {
        return try {
            val data = ByteArray(stream.available())
            stream.read(data, 0, data.size)
            data
        } catch (e: Exception) {
            ByteArray(0)
        } finally {
            try {
                stream.close()
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    /**
     * WAV adat konvertálása PCM formátumú lebegőpontos tömbbé.
     *
     * @param data A WAV fájl adatai `ByteArray` formátumban.
     * @return A PCM adatok `DoubleArray` formátumban, vagy üres tömb, ha az input üres.
     */
    fun wavToPcm(data: ByteArray): DoubleArray {
        if (data.isEmpty()) {
            return DoubleArray(0)
        }
        val d = DoubleArray(data.size / 2)
        val maxShortValue = Short.MAX_VALUE.toDouble()
        for (i in 0 until data.size / 2) {
            d[i] = ((((data[2 * i + 1].toInt() and 0xFF) shl 8) + (data[2 * i].toInt() and 0xFF)).toShort()) / maxShortValue
        }
        return d
    }

    /**
     * PCM formátumú lebegőpontos hangminták konvertálása 16 bites PCM formátumú bájttömbbé.
     *
     * @param samples A lebegőpontos hangminták `DoubleArray` formátumban.
     * @return A 16 bites PCM formátumú hangminták `ByteArray` formátumban.
     */
    fun convertTo16BitPCM(samples: DoubleArray): ByteArray {
        val generatedSound = ByteArray(2 * samples.size)
        var index = 0
        for (sample in samples) {
            val maxSample = (sample * Short.MAX_VALUE).toInt()
            generatedSound[index++] = (maxSample and 0x00ff).toByte()
            generatedSound[index++] = ((maxSample and 0xff00) ushr 8).toByte()
        }
        return generatedSound
    }

    /**
     * Szinuszhullám generálása a megadott mintaszám, mintavételi frekvencia és hangfrekvencia alapján.
     *
     * @param samples A generálandó minták száma.
     * @param sampleRate A hang mintavételezési frekvenciája Hz-ben.
     * @param toneFrequency A generált szinuszhullám frekvenciája Hz-ben.
     * @return A szinuszhullám mintái `DoubleArray` formátumban.
     */
    fun getSine(samples: Int, sampleRate: Int, toneFrequency: Double): DoubleArray {
        val sample = DoubleArray(samples)
        for (i in 0 until samples) {
            sample[i] = sin(2 * Math.PI * i / (sampleRate / toneFrequency))
        }
        return sample
    }
}


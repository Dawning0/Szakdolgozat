package com.example.metronome.beater.metronome.types

import android.content.Context
import com.example.metronome.beater.audio.AudioUtils

/**
 * Egy szinuszhullám-alapú metronóm megvalósítása, amely a különböző hangfrekvenciákat használja
 * az ütem, a tick és az alosztás megjelölésére.
 *
 * @param sampleRate A mintavételezési frekvencia Hertzben.
 * @param context Az Android `Context` objektuma.
 */
class SineMetronome(sampleRate: Int, context: Context) : Metronome(sampleRate, context) {

    /**
     * Az ütem frekvenciája Hertzben.
     */
    var beatFrequency = 3000.0

    /**
     * A tick frekvenciája Hertzben.
     */
    var soundFrequency = 10000.0

    /**
     * Az alosztási frekvencia Hertzben.
     */
    var subDivFreq = 7000.0

    /**
     * Az ütem hangjának visszaadása szinuszhullámként.
     *
     * @return Az ütem hangját reprezentáló `DoubleArray`.
     */
    override fun getBeatSound(): DoubleArray {
        return if (beatEnabled)
            AudioUtils.getSine(tick, sampleRate, beatFrequency)
        else
            AudioUtils.getSine(tick, sampleRate, soundFrequency)
    }

    /**
     * A tick hangjának visszaadása szinuszhullámként.
     *
     * @return A tick hangját reprezentáló `DoubleArray`.
     */
    override fun getTickSound(): DoubleArray = AudioUtils.getSine(tick, sampleRate, soundFrequency)

    /**
     * Az alosztási hang visszaadása szinuszhullámként.
     *
     * @return Az alosztási hangot reprezentáló `DoubleArray`.
     */
    override fun getSubDivSound(): DoubleArray = AudioUtils.getSine(tick, sampleRate, subDivFreq)
}

package com.example.metronome.beater.metronome.types

import android.content.Context
import com.example.metronome.beater.audio.AudioUtils

/**
 * Egy konkrét metronóm osztály, amely támogatja a különböző minták és hangok beállítását.
 *
 * @param sampleRate A mintavételezési frekvencia Hertzben.
 * @param context Az Android `Context` objektuma.
 */
class PatternMetronome(sampleRate: Int, context: Context) : Metronome(sampleRate, context) {

    // A hangminták különböző típusai.
    private var beatWav: DoubleArray? = null
    private var soundWav: DoubleArray? = null
    private var subdivWav: DoubleArray? = null

    /**
     * Az ütem hangja.
     */
    lateinit var beatSound: MetronomeSound
        private set

    /**
     * A tick hangja.
     */
    lateinit var tickSound: MetronomeSound
        private set

    /**
     * Az alosztási hang.
     */
    var subDivSound: MetronomeSound? = null
        private set

    init {
        setBeatSound(MetronomeSound.CLAVES_1)
        setTickSound(MetronomeSound.CLAVES_2)
        setSubDivSound(MetronomeSound.CLAVES_3)
    }

    /**
     * Beállítja az ütem hangját.
     *
     * @param metronomeSound Az ütem hangját reprezentáló `MetronomeSound` érték.
     */
    fun setBeatSound(metronomeSound: MetronomeSound) {
        this.beatSound = metronomeSound
        if (metronomeSound == MetronomeSound.OFF) {
            this.beatWav = null
            return
        }
        this.beatWav = readWaveData(metronomeSound.resource)
    }

    /**
     * Beállítja a tick hangját.
     *
     * @param metronomeSound A tick hangját reprezentáló `MetronomeSound` érték.
     */
    fun setTickSound(metronomeSound: MetronomeSound) {
        this.tickSound = metronomeSound
        if (metronomeSound == MetronomeSound.OFF) {
            this.soundWav = null
            return
        }
        this.soundWav = readWaveData(metronomeSound.resource)
    }

    /**
     * Beállítja az alosztási hangot.
     *
     * @param metronomeSound Az alosztási hangot reprezentáló `MetronomeSound` érték.
     */
    fun setSubDivSound(metronomeSound: MetronomeSound) {
        this.subDivSound = metronomeSound
        if (metronomeSound == MetronomeSound.OFF) {
            this.subdivWav = null
            return
        }
        this.subdivWav = readWaveData(metronomeSound.resource)
    }

    /**
     * Az ütem hangjának visszaadása.
     *
     * @return Az ütem hangját reprezentáló `DoubleArray`, vagy `null`, ha nincs beállítva.
     */
    override fun getBeatSound(): DoubleArray? {
        return if (this.beatEnabled) beatWav else soundWav
    }

    /**
     * A tick hangjának visszaadása.
     *
     * @return A tick hangját reprezentáló `DoubleArray`, vagy `null`, ha nincs beállítva.
     */
    override fun getTickSound(): DoubleArray? = soundWav

    /**
     * Az alosztási hang visszaadása.
     *
     * @return Az alosztási hangot reprezentáló `DoubleArray`, vagy `null`, ha nincs beállítva.
     */
    override fun getSubDivSound(): DoubleArray? = subdivWav

    /**
     * Beolvassa a hangadatokat a megadott erőforrásból.
     *
     * @param id Az erőforrás azonosítója.
     * @return A beolvasott PCM adatok `DoubleArray` formátumban.
     */
    private fun readWaveData(id: Int): DoubleArray {
        context.resources.openRawResource(id).use { input ->
            val wavData = AudioUtils.readWav(input)
            return AudioUtils.wavToPcm(wavData)
        }
    }
}

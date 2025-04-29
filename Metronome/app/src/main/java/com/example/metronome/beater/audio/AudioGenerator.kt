package com.example.metronome.beater.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack

/**
 * Egy osztály, amely hangot generál és lejátszik az `AudioTrack` API segítségével.
 *
 * @param sampleRate A hang mintavételezési frekvenciája Hertzben (pl. 44100 CD-minőségű hanghoz).
 * @param volume A kezdeti hangerő szintje (0-100).
 */
class AudioGenerator(private val sampleRate: Int, volume: Int) {
    private var volume = volume / 100f

    private var audioTrack: AudioTrack? = null

    /**
     * Beállítja a hang hangerőszintjét.
     *
     * @param volume A kívánt hangerő szintje (0-100).
     */
    fun setVolume(volume: Int) {
        this.volume = volume / 100f
    }

    /**
     * Inicializálja az `AudioTrack` példányt a meghatározott hangattribútumokkal és formátummal.
     */
    fun initializeTrack() {
        val attributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()

        val format = AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .setSampleRate(sampleRate)
            .build()

        audioTrack = AudioTrack(attributes, format, sampleRate, AudioTrack.MODE_STREAM, AudioManager.AUDIO_SESSION_ID_GENERATE)
    }

    /**
     * Elindítja a hangfolyam lejátszását.
     */
    fun startPlay() {
        audioTrack?.play()
    }

    /**
     * Leállítja és felszabadítja az `AudioTrack` példányt.
     */
    fun destroyAudioTrack() {
        audioTrack?.stop()
        audioTrack?.release()
    }

    /**
     * Hangadatokat ír az `AudioTrack` példányhoz lejátszás céljából.
     *
     * @param samples Egy tömb, amely audio mintákat tartalmaz lebegőpontos formátumban, amelyet 16 bites PCM formátumra kell konvertálni.
     */
    fun writeSound(samples: DoubleArray) {
        audioTrack?.let {
            val generated = AudioUtils.convertTo16BitPCM(samples)
            it.setVolume(volume)
            it.write(generated, 0, generated.size)
        }
    }
}


package com.example.metronome.beater.metronome.types

import android.content.Context
import android.util.Log
import com.example.metronome.beater.audio.AudioGenerator
import com.example.metronome.beater.audio.AudioUtils
import com.example.metronome.beater.metronome.TimeSignature

/**
 * Egy absztrakt metronóm osztály, amely különböző típusú metronómok alapjait határozza meg.
 *
 * @param sampleRate A mintavételezési frekvencia Hertzben.
 * @param context Az Android `Context` objektum.
 */
sealed class Metronome(val sampleRate: Int, val context: Context) {

    // Változók, amelyek az osztály állapotát szabályozzák.
    var subDivEnabled: Boolean = false // Az alosztások engedélyezése.
    var beatEnabled: Boolean = false   // Az ütemhang engedélyezése.
    var dirty: Boolean = false         // Az állapot frissítését jelző zászló.
    var tempo: Int = 60                // A metronóm sebessége BPM-ben.
    var volume: Int = 100              // A hang szintje (0–100).
    var isPlaying: Boolean = false     // A metronóm lejátszási állapota.
    var timeSignature: TimeSignature = TimeSignature.COMMON_TIME // Az ütemmutató.
    var onUpdated: () -> Unit = {}    // Az állapot frissítésekor meghívott függvény.

    protected val tick = 3000 // Az egy hangjelzés hossza mintákban.
    private val audioGenerator = AudioGenerator(sampleRate, volume)

    /**
     * A metronóm leállítása.
     */
    fun stop() {
        if (isPlaying) {
            isPlaying = false
            try {
                audioGenerator.destroyAudioTrack()
            } catch (_: Exception) {
                // Kivétel kezelése figyelmen kívül hagyva.
            }
        }
    }

    /**
     * Elindítja a metronóm hangjelzéseit az aktuális beállítások alapján.
     *
     * - Inicializálja az audió sávot az `audioGenerator` segítségével.
     * - Kiszámolja a csend hosszát az ütemek között a `calculateSilence()` függvénnyel.
     * - Generálja a különböző hangmintákat:
     *   - `tickSound`: Az alap hangjelzés.
     *   - `tickBeat`: Az ütem első hangsúlyának hangja.
     *   - `tickSubDiv`: Az alosztási hang (ha engedélyezve van).
     * - Egy ciklusban folyamatosan kitölti a `sound` tömböt a hangmintákkal és a csenddel.
     * - A `volume` változó alapján beállítja a hangerőt.
     * - Amíg a `isPlaying` true, lejátssza a generált hangmintákat.
     * - Ha a `dirty` változó igaz, frissíti a beállításokat és újrakezdi a lejátszást.
     */

    fun play() {
        audioGenerator.initializeTrack()
        val silence = calculateSilence()
        Log.d("Metronome", "Silence $silence")

        val tickSubDiv = if (subDivEnabled) getSubDivSound() ?: AudioUtils.getSine(tick, sampleRate, 0.0) else AudioUtils.getSine(tick, sampleRate, 0.0)
        val tickBeat = getBeatSound() ?: AudioUtils.getSine(tick, sampleRate, 0.0)
        val tickSound = getTickSound() ?: AudioUtils.getSine(tick, sampleRate, 0.0)

        val sound = DoubleArray(sampleRate)
        var t = 0 // tick számláló
        var s = 0 // silence számláló
        var b = 0 // beat számláló

        audioGenerator.startPlay()
        do {
            for (i in sound.indices) {
                if (!isPlaying) break

                if (t < this.tick) {
                    if (b % timeSignature.subdiv == 0) {
                        sound[i] = tickBeat[t]
                    } else {
                        sound[i] = if (subDivEnabled && (b + 1) % timeSignature.subdiv == 0) tickSubDiv[t] else tickSound[t]
                    }
                    t += 1
                    continue
                }

                sound[i] = 0.0
                s += 1
                if (s >= silence) {
                    t = 0
                    s = 0
                    b += 1
                    if (b >= (timeSignature.numerator * timeSignature.subdiv)) {
                        b = 0
                    }
                }
            }

            audioGenerator.setVolume(volume)
            audioGenerator.writeSound(sound)
            if (dirty) {
                break
            }
        } while (isPlaying)

        if (dirty) {
            dirty = false
            onUpdated()
            play()
        }
    }

    /**
     * Az ütemhang definiálása.
     * @return Az ütemhang mintái `DoubleArray` formátumban, vagy `null`.
     */
    abstract fun getBeatSound(): DoubleArray?

    /**
     * A hangjelző hang definíciója.
     * @return A hangjelző hang mintái `DoubleArray` formátumban, vagy `null`.
     */
    abstract fun getTickSound(): DoubleArray?

    /**
     * Az alosztási hang definíciója.
     * @return Az alosztási hang mintái `DoubleArray` formátumban, vagy `null`.
     */
    abstract fun getSubDivSound(): DoubleArray?

    /**
     * Kiszámítja a csend hosszát az ütemek között.
     *
     * @return A csend hossza mintákban.
     */
    private fun calculateSilence(): Int {
        val bpm = tempo.toDouble() * timeSignature.subdiv
        Log.d("Metronome", "Tempo: $tempo | Bpm: $bpm")
        return (((60.0 / bpm) * sampleRate) - tick).toInt()
    }
}


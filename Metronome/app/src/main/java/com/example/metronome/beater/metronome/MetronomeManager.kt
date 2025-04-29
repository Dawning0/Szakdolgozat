package com.example.metronome.beater.metronome

import com.example.metronome.beater.App
import com.example.metronome.beater.metronome.types.Metronome
import com.example.metronome.beater.metronome.types.PatternMetronome
import com.example.metronome.beater.metronome.types.SineMetronome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.concurrent.thread
import android.os.Process

const val SAMPLE_RATE = 48000

/**
 * A metronóm kezeléséért felelős osztály, amely kezeli a különböző metronóm típusokat és azok beállításait,
 * valamint vezérli a vizuális metronómot.
 *
 * @param app Az alkalmazás fő objektuma.
 */
class MetronomeManager(val app: App) {

    /**
     * A vizuális metronóm objektuma.
     */
    val visualMetronome = VisualMetronome(app.appContext)

    /**
     * Az aktuális metronóm típusa.
     */
    private lateinit var currentType: MetronomeType

    /**
     * Az aktuális metronóm példány.
     */
    lateinit var currentMetronome: Metronome
        private set

    /**
     * Az aktuális metronóm futtatásához tartozó szál.
     */
    private lateinit var job: Thread

    /**
     * A vizuális metronóm engedélyezve van-e.
     */
    private var visualEnabled: Boolean = true

    /**
     * Az alosztások állapota.
     */
    var subDiv = MutableStateFlow(false)

    /**
     * A metronóm futási állapota.
     */
    val running = MutableStateFlow(false)

    /**
     * Az aktuális tempó BPM-ben.
     */
    var tempo = MutableStateFlow(120)

    /**
     * Az aktuális ütemmutató.
     */
    val timeSignature = MutableStateFlow("4/4")

    /**
     * Az aktuális hangerő szintje.
     */
    var currentVolume: Int = 100

    init {
        initDefaults()
    }

    /**
     * Az alapértelmezett beállítások inicializálása.
     */
    fun initDefaults() {
        this.switchType(MetronomeType.PATTERN)
        this.setTimeSignature(TimeSignature.COMMON_TIME)
        this.setSubDivisions(false)
        this.setBeat(true)
        this.setTempo(120)
        this.setVolume(100)
    }

    /**
     * Beállítja, hogy az alosztások engedélyezve vannak-e.
     *
     * @param enabled `true`, ha az alosztások engedélyezve vannak, különben `false`.
     */
    fun setSubDivisions(enabled: Boolean) {
        this.currentMetronome.subDivEnabled = enabled
        this.visualMetronome.subDiv = enabled
        this.subDiv.update { enabled }
    }

    /**
     * Beállítja, hogy az ütemhang engedélyezve van-e.
     *
     * @param enabled `true`, ha az ütemhang engedélyezve van, különben `false`.
     */
    fun setBeat(enabled: Boolean) {
        this.currentMetronome.beatEnabled = enabled
        this.visualMetronome.beat = enabled
    }

    /**
     * Beállítja a metronóm tempóját BPM-ben.
     *
     * @param tempo Az új tempó értéke (maximum 300).
     */
    fun setTempo(tempo: Int) {
        val cappedTempo = if (tempo > 300) 300 else tempo
        this.tempo.update { cappedTempo }
        this.currentMetronome.tempo = tempo
        this.visualMetronome.tempo = tempo
    }

    /**
     * Beállítja a metronóm hangerőszintjét.
     *
     * @param volume Az új hangerő szintje (0–100).
     */
    fun setVolume(volume: Int) {
        this.currentMetronome.volume = volume
        this.currentVolume = volume
    }

    /**
     * Beállítja az ütemmutatót.
     *
     * @param timeSignature Az új ütemmutató.
     */
    fun setTimeSignature(timeSignature: TimeSignature) {
        this.currentMetronome.timeSignature = timeSignature
        this.visualMetronome.changeTimeSignature(timeSignature)
        this.timeSignature.update { "${timeSignature.numerator}/${timeSignature.subdiv}" }
    }

    /**
     * Átvált a metronóm típusok között.
     *
     * @param metronomeType Az új metronóm típus.
     */
    private fun switchType(metronomeType: MetronomeType) {
        this.currentType = metronomeType
        this.currentMetronome = when (metronomeType) {
            MetronomeType.SINE -> SineMetronome(SAMPLE_RATE, app.appContext)
            MetronomeType.PATTERN -> PatternMetronome(SAMPLE_RATE, app.appContext)
        }
    }

    /**
     * Frissíti a metronóm állapotát, például megújítja a vizuális metronómot.
     */
    fun refresh() {
        this.currentMetronome.onUpdated = {
            this.visualMetronome.stop()
            this.visualMetronome.start()
        }
        if (this.currentMetronome.isPlaying) {
            this.currentMetronome.dirty = true
        }
        this.visualMetronome.refresh()
    }

    /**
     * Leállítja a metronómot és a vizuális metronómot.
     */
    fun stop() {
        this.currentMetronome.stop()
        visualMetronome.stop()
        running.update { false }
    }

    /**
     * Elindítja a metronómot és a vizuális metronómot.
     */
    fun start() {
        if (!this.visualMetronome.running && this.visualEnabled) {
            visualMetronome.start()
        }

        if (!this.currentMetronome.isPlaying) {
            this.currentMetronome.isPlaying = true

            this.job = thread {
                try {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO)
                    while (currentMetronome.isPlaying) {
                        currentMetronome.play()
                    }
                } catch (e: Exception){
                    e.printStackTrace()
                }

            }
        }

        running.update { true }
    }
}

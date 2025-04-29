package com.example.metronome.beater.metronome

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import android.content.Context
import android.util.DisplayMetrics


/**
 * Egy metronóm vizuális megjelenítéséért felelős osztály, amely az ütemek és alosztások
 * állapotának vizuális visszajelzését biztosítja.
 *
 * Ez az osztály korutinokat használ az állapot frissítéséhez és a vizuális jelzők
 * megjelenítéséhez, az aktuális tempó és ütemmutató alapján.
 */
class VisualMetronome(context: Context) {

    companion object {
        /**
         * A vizuális elemek állandói:
         * - [lightEmpty]: Az üres (kikapcsolt) állapotú jelző.
         * - [lightFill]: Egy részlegesen kitöltött (aktivált) jelző.
         * - [lightFull]: Egy teljesen kitöltött (kiemelt) jelző.
         */
        const val lightEmpty = 0
        const val lightFill = 1
        const val lightFull = 2
    }

    var maxIndicators: Int = calculateMaxIndicators(context)

    /**
     * Az aktuális futási állapot jelzője.
     *
     * - `true`, ha a vizuális metronóm fut.
     * - `false`, ha le van állítva.
     */
    var running = false
        private set

    /**
     * A metronóm aktuális tempója BPM-ben (ütem / perc).
     */
    var tempo = 120

    /**
     * Meghatározza, hogy az ütemhangok megjelenítve legyenek-e.
     */
    var beat: Boolean = true

    /**
     * Meghatározza, hogy az alosztások vizuálisan megjelenítve legyenek-e.
     */
    var subDiv: Boolean = false

    /**
     * Az aktuális ütemmutató, amely meghatározza az ütemek és alosztások logikáját.
     */
    private var timeSignature: TimeSignature = TimeSignature.COMMON_TIME

    /**
     * A vizuális jelzők állapota.
     * - Minden elem az aktuális állapotot tartalmazza:
     *   - [lightEmpty]: Üres állapot.
     *   - [lightFill]: Kitöltött állapot.
     *   - [lightFull]: Kiemelt állapot.
     */
    val state = MutableStateFlow(IntArray(0))

    /**
     * A korutin kezelésére szolgáló `Job` objektum, amely a frissítési ciklust kezeli.
     */
    private var job: Job? = null

    /**
     * Egy `CoroutineScope` objektum, amely kezeli a korutinok életciklusát.
     */
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    /**
     * Elindítja a vizuális metronómot az aktuális tempóval és ütemmutatóval.
     *
     * @throws IllegalStateException Ha a korutin már fut, nem indítható újra.
     */
    fun start() {
        if (job != null) return // Megakadályozza több korutin létrehozását

        val interval = calculateInterval() // Intervallum kiszámítása az ütemek között

        job = scope.launch {
            var indicatorIndex = 0
            while (isActive) {
                // Frissíti az aktuális index állapotát
                updateState(indicatorIndex, if (beat && indicatorIndex == 0) lightFull else lightFill)

                // Tovább lép a következő kijelzőre, visszaugrik az elejére, ha szükséges
                indicatorIndex = (indicatorIndex + 1) % state.value.size

                // Várakozás a következő frissítésig
                delay(interval)
            }
        }
        running = true
    }

    /**
     * Leállítja a vizuális metronómot és visszaállítja az állapotot.
     */
    fun stop() {
        job?.cancel() // Megszakítja a korutint
        job = null
        state.update { IntArray(it.size) { lightEmpty } } // Az összes kijelző állapotának alaphelyzetbe állítása
        running = false
    }

    /**
     * Módosítja az aktuális ütemmutatót és frissíti a kijelzőket.
     *
     * @param timeSignature Az új ütemmutató.
     */
    fun changeTimeSignature(timeSignature: TimeSignature) {
        this.timeSignature = timeSignature
        refresh()
    }

    /**
     * Frissíti a kijelzők állapotát az aktuális ütemmutató alapján.
     */
    fun refresh() {
        val indicator = timeSignature.indicatorCount
        val limitedIndicator = Math.min(indicator, maxIndicators)
        state.update { IntArray(limitedIndicator) { lightEmpty } }
    }

    /**
     * Kiszámítja az intervallumot a vizuális frissítések között milliszekundumban.
     *
     * @return Az intervallum milliszekundumban.
     */
    private fun calculateInterval(): Long {
        return if (subDiv) {
            (60000 / (tempo * timeSignature.subdiv)).toLong()
        } else {
            (60000 / tempo).toLong()
        }
    }

    /**
     * Frissíti egy adott index állapotát a kijelzők között.
     *
     * @param index Az állapotot frissítendő index.
     * @param value Az új állapotérték.
     */
    private fun updateState(index: Int, value: Int) {
        state.update {
            val updatedState = it.copyOf()
            for (i in updatedState.indices) {
                updatedState[i] = if (i == index) value else lightEmpty
            }
            updatedState
        }
    }
    /**
     * Meghatározza a kijelzők maximális számát a képernyő mérete alapján.
     *
     * @param context Az alkalmazás kontextusa.
     * @return A kiszámított maximális kijelzők száma.
     */
    private fun calculateMaxIndicators(context: Context): Int{
        val metrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidthDp = metrics.widthPixels / metrics.density
        return  if(screenWidthDp < 600){
            4
        } else {
            8
        }
    }
}

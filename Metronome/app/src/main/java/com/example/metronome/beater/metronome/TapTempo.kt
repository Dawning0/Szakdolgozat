package com.example.metronome.beater.metronome

/**
 * Egy osztály, amely lehetővé teszi a tempó beállítását a felhasználó által végrehajtott koppintások alapján.
 *
 * @param metronomeManager A metronóm vezérléséért felelős `MetronomeManager` példány.
 */
class TapTempo(private val metronomeManager: MetronomeManager) {

    // Az első koppintás időbélyege milliszekundumban.
    private var groundZero: Long = 0L

    // Az utolsó koppintás időbélyege milliszekundumban.
    private var lastTap: Long = 0L

    // Az utolsó előtti koppintás időbélyege milliszekundumban.
    private var previousTap: Long = 0L

    // Az első koppintás óta eltelt idő milliszekundumban.
    private var tapDiff: Long = 0

    // A koppintások számlálója.
    private var counter: Int = 0

    // Az utolsó két koppintás közötti eltelt idő milliszekundumban.
    private var elapsed: Long = 0L

    /**
     * Kezeli a felhasználó koppintásait, és kiszámítja az ezek alapján meghatározott BPM értéket.
     */
    fun tap() {
        if (lastTap == 0L) {
            groundZero = System.currentTimeMillis()
            counter = 0
        }

        lastTap = System.currentTimeMillis()
        elapsed = lastTap - previousTap
        previousTap = lastTap
        tapDiff = lastTap - groundZero

        if (tapDiff != 0L) {
            val bpm = (60000 * counter / tapDiff).toInt()
            metronomeManager.setTempo(bpm)
            metronomeManager.refresh()
        }

        counter += 1
        if (elapsed > 3000) {
            lastTap = 0
        }
    }
}

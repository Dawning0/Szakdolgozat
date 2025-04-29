package com.example.metronome.beater.metronome.types

import com.example.metronome.R

/**
 * Az elérhető metronóm hangokat definiáló felsorolási osztály (enum).
 *
 * @property displayName Az erőforrás azonosítója, amely a hang megjelenített nevét tartalmazza.
 * @property resource Az erőforrás azonosítója, amely a hangfájlt tartalmazza.
 */
enum class MetronomeSound(val displayName: Int, val resource: Int) {

    /**
     * Hang kikapcsolása.
     */
    OFF(R.string.soundOff, -1),

    /**
     * Beep hang.
     */
    BEEP(R.string.beep, R.raw.beep),

    /**
     * Bongo hang.
     */
    BONGO(R.string.bongo, R.raw.bongo),

    /**
     * Clap hang.
     */
    CLAP(R.string.clap, R.raw.clap),

    /**
     * Első claves hang.
     */
    CLAVES_1(R.string.claves1, R.raw.claves1),

    /**
     * Második claves hang.
     */
    CLAVES_2(R.string.claves2, R.raw.claves2),

    /**
     * Harmadik claves hang.
     */
    CLAVES_3(R.string.claves3, R.raw.claves3),

    /**
     * Cowbell (kolomp) hang.
     */
    COWBELL(R.string.cowbell, R.raw.cowbell),

    /**
     * Első metronóm hang.
     */
    METRO_1(R.string.metro1, R.raw.metro1),

    /**
     * Második metronóm hang.
     */
    METRO_2(R.string.metro2, R.raw.metro2),

    /**
     * Harmadik metronóm hang.
     */
    METRO_3(R.string.metro3, R.raw.metro3),

    /**
     * Rim hang.
     */
    RIM(R.string.rim, R.raw.rim),

    /**
     * Sticks (ütőpálcák) hang.
     */
    STICKS(R.string.sticks, R.raw.sticks),

    /**
     * Fa ütőhangszer hang.
     */
    WOOD(R.string.wood, R.raw.wood);
}

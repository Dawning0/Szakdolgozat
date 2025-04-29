package com.example.metronome.beater


import android.content.Context
import com.example.metronome.beater.metronome.MetronomeManager

/**
 * Az alkalmazás főosztálya, amely az alkalmazás szintű inicializációkat és menedzsereket kezeli.
 *
 * @param appContext Az Android `Context`, amely az alkalmazás környezetére vonatkozik.
 * @property metronomeManager Egy lusta inicializálású `MetronomeManager`, amely a metronóm működését kezeli.
 */
class App(val appContext: Context) {

    /**
     * A metronóm vezérléséért felelős menedzser. A lusta inicializálás biztosítja, hogy csak akkor
     * jöjjön létre, amikor először hozzáférnek.
     */
    val metronomeManager: MetronomeManager by lazy { MetronomeManager(this) }

}
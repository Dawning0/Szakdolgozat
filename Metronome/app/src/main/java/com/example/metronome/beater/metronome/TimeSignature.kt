package com.example.metronome.beater.metronome

/**
 * Egy osztály, amely egy zenei ütemmutatót reprezentál.
 *
 * @property numerator Az ütemmutató számlálója (pl. 4/4 esetén 4).
 * @property subdiv Az ütemmutató alosztásának értéke (pl. 4/4 esetén 1).
 * @throws IllegalArgumentException Ha a számláló nem 1 és 20 között van, vagy az alosztás nem 1 és 4 között van.
 */
data class TimeSignature(val numerator: Int, val subdiv: Int) {
    init {
        require(numerator in 1..20) { "1 és 20 közötti érték" }
        require(subdiv in 1..4) { "1 és 4 közötti érték" }
    }

    /**
     * Az ütemjelzők száma, amely az ütemmutató számláló értékét adja vissza.
     */
    val indicatorCount: Int
        get() = numerator

    companion object {
        /**
         * Egy általánosan használt ütemmutató, amely 4/4-es időtartamot reprezentál.
         */
        val COMMON_TIME = TimeSignature(4, 1)
    }
}


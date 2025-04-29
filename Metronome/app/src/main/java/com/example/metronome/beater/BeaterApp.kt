package com.example.metronome.beater

import android.app.Application

/**
 * Az alkalmazás alapvető osztálya, amely az Android `Application` osztályból származik.
 * Ez kezeli az alkalmazás életciklusának inicializálását és globális állapotait.
 */
class BeaterApp : Application(){

 companion object{
     /**
      * Az alkalmazás verziószáma.
      */
    val appVersion = "0.1.0"
     /**
      * Egy `App` példány, amely globális hozzáférést biztosít az alkalmazás szolgáltatásaihoz és erőforrásaihoz.
      */
    lateinit var app: App
 }
    /**
     * Az alkalmazás inicializálása az indításkor.
     * Itt történik az `App` példány létrehozása.
     */
    override fun onCreate() {
        super.onCreate()
        app = App(this)
    }
}
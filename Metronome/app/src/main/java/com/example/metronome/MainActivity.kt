package com.example.metronome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.metronome.ui.pages.MetronomePage
import com.example.metronome.ui.theme.MetronomeTheme
/**
 * Az alkalmazás fő aktivitása, amely az indítástól kezdve kezeli az alkalmazás megjelenítését és életciklusát.
 * Ez a komponens a Material 3 témát és a metronóm oldalt jeleníti meg.
 */
class MainActivity : ComponentActivity() {
    /**
     * Az aktivitás indításakor meghívott metódus.
     * Inicializálja az Edge-to-Edge megjelenítést, és beállítja a Compose tartalmat.
     *
     * @param savedInstanceState Az előző állapotot tartalmazó csomag, ha rendelkezésre áll.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MetronomeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MetronomePage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

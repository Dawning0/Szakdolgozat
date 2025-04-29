package beater.ui.components.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Egy felsorolási osztály (sealed class), amely az alsó navigációs sávban megjelenő elemeket definiálja.
 *
 * @property route Az elem navigációs útvonala.
 * @property icon Az elemhez tartozó ikon.
 * @property label Az elemhez tartozó címke.
 */
sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {

    /**
     * Kapcsolódási képernyő (GigServer) navigációs elem.
     */
    data object Connection : BottomNavItem("connection", Icons.Default.Info, "GigServer")

    /**
     * Profil képernyő navigációs elem.
     */
    data object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")

    /**
     * Metronóm képernyő navigációs elem.
     */
    data object Metronome : BottomNavItem("metronome", Icons.Default.PlayArrow, "Metronome")
}

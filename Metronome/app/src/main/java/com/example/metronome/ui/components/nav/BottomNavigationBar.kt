package com.example.metronome.ui.components.nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
/**
 * Egy alsó navigációs sávot (Bottom Navigation Bar) megvalósító Compose-függvény.
 * Ez a sáv lehetővé teszi, hogy a felhasználók különböző képernyők között navigáljanak.
 *
 * @param navController A `NavController`, amely a navigációs állapotot kezeli.
 * @param items Az alsó navigációs sávban megjelenő elemek listája.
 */
@Composable
fun BottomNavigationBar(navController: NavController, items: List<BottomNavItem>) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                label = { Text(text = screen.label) },
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.label) },
                onClick = {
                    navController.navigate(screen.route)
                },
            )
        }
    }
}
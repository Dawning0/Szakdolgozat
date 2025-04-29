package com.example.metronome.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Egy időmutató választó komponens, amely lehetővé teszi a felhasználó számára az ütemmutató
 * számlálójának (numerator) és nevezőjének (denominator) beállítását.
 *
 * @param initialNumerator Az ütemmutató kezdeti számlálója (alapértelmezett érték: 4).
 * @param initialDenominator Az ütemmutató kezdeti nevezője (alapértelmezett érték: 1).
 * @param onTimeSignatureChanged Egy visszahívási függvény, amelyet az ütemmutató megváltoztatásakor hív meg a komponens.
 */
@Composable
fun TimeSignatureSelector(
    initialNumerator: Int = 4,
    initialDenominator: Int = 1,
    onTimeSignatureChanged: (Int, Int) -> Unit
) {
    var numerator by remember { mutableStateOf(initialNumerator) }
    var denominator by remember { mutableStateOf(initialDenominator) }

    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(16.dp)
            .background(Color(0xff111113), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp) // Compact spacing
    ) {
        // Cím.
        Text(
            text = "Time Signature",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Cyan
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Időmutató bemenetek.
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Számláló (numerator) választó.
            NumberPicker(
                value = numerator,
                onValueChange = {
                    numerator = it
                    onTimeSignatureChanged(numerator, denominator)
                },
                range = 1..20
            )

            Text(
                text = "/",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp) // Divider between numerator and denominator
            )

            // Nevező (denominator/subdiv) választó.
            NumberPicker(
                value = denominator,
                onValueChange = {
                    denominator = it
                    onTimeSignatureChanged(numerator, denominator)
                },
                range = 1..4
            )
        }
    }
}

/**
 * Egy számválasztó komponens, amely lehetővé teszi egy érték növelését vagy csökkentését egy megadott tartományon belül.
 *
 * @param value Az aktuálisan kiválasztott érték.
 * @param onValueChange Egy visszahívási függvény, amelyet az érték megváltoztatásakor hív meg a komponens.
 * @param range Az érvényes értékek tartománya.
 */
@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // "+" gomb.
        Button(
            onClick = { if (value < range.last) onValueChange(value + 1) },
            modifier = Modifier
                .size(40.dp)
                .background(Color.Cyan, shape = CircleShape), // Circular shape
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff1e1f22),
                contentColor = Color.Cyan
            )
        ) {
            Text("+",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White)
        }

        // Kiválasztott érték megjelenítése.
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(vertical = 8.dp) // Space between the value and buttons
        )

        // "-" gomb.
        Button(
            onClick = { if (value > range.first) onValueChange(value - 1) },
            modifier = Modifier
                .size(40.dp) // Circular size
                .background(Color.Cyan, shape = CircleShape), 
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff1e1f22),
                contentColor = Color.Cyan
            )
        ) {
            Text("-",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White)
        }
    }
}




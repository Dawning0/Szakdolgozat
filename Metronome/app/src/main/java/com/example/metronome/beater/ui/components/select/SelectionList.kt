package beater.ui.components.select

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
/**
 * Egy lenyíló listát megvalósító Compose-függvény, amely lehetővé teszi a felhasználó számára
 * egy adott opció kiválasztását.
 *
 * @param items A kiválasztható elemek listája.
 * @param selectedIndex Az alapértelmezett kiválasztott elem indexe.
 * @param modifier A `Modifier` az egyes komponensek testreszabásához.
 * @param onSelected A kiválasztott elem indexének visszaadása, amikor egy elemre kattintanak.
 */
@Composable
fun SelectionList(items: List<String>, selectedIndex: Int, modifier: Modifier, onSelected: (Int) -> Unit) {
    var showDropdown by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier.background(Color(0xff141414), RoundedCornerShape(10.dp)).clickable { showDropdown = !showDropdown },
        contentAlignment = Alignment.Center
    ) {
        Text(text = items.getOrElse(selectedIndex) { "Please select" }.toString(), modifier = Modifier.padding(10.dp), color = Color.Cyan, fontSize = TextUnit(20f, TextUnitType.Sp))
    }

    Box() {
        if (showDropdown) {
            Popup(
                alignment = Alignment.TopCenter,
                properties = PopupProperties(
                    excludeFromSystemGesture = true,
                ),
                // to dismiss on click outside
                onDismissRequest = { showDropdown = false }
            ) {

                Column(
                    modifier = modifier
                        .padding(10.dp)
                        .heightIn(max = 200.dp)
                        .verticalScroll(state = scrollState)
                        .border(width = 1.dp, color = Color(0xff141414)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    items.onEachIndexed { index, item ->
                        Box(
                            modifier = Modifier
                                .background(Color(0xff1c1c22))
                                .padding(10.dp)
                                .fillMaxWidth()
                                .clickable {
                                    onSelected(index)
                                    showDropdown = !showDropdown
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = item, color = Color.Cyan, fontSize = TextUnit(25f, TextUnitType.Sp))
                        }
                    }

                }
            }
        }
    }
}
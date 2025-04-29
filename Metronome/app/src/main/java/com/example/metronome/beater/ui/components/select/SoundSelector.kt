package beater.ui.components.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import beater.metronome.MetronomeManager
import beater.metronome.types.MetronomeSound
import beater.metronome.types.PatternMetronome

/**
 * Egy Compose-komponens, amely lehetővé teszi a metronóm különböző hangjainak kiválasztását,
 * beleértve az ütem (beat), hangjelzés (tick) és alosztási (subdivision) hangokat.
 *
 * @param metronomeManager A metronóm vezérléséért felelős `MetronomeManager` példány.
 * @param modifier A `Modifier`, amely a komponens megjelenését és elrendezését szabályozza.
 */
@Composable
fun SoundSelector(metronomeManager: MetronomeManager, modifier: Modifier) {
    val currentMetronome = metronomeManager.currentMetronome

    if (currentMetronome is PatternMetronome) {
        val list = MetronomeSound.entries.map { stringResource(id = it.displayName) }.toList()

        var currentBeat by remember {
            mutableIntStateOf(
                MetronomeSound.entries.indexOf(
                    currentMetronome.beatSound
                )
            )
        }
        var currentTick by remember {
            mutableIntStateOf(
                MetronomeSound.entries.indexOf(
                    currentMetronome.tickSound
                )
            )
        }
        var currentSubDiv by remember {
            mutableIntStateOf(
                MetronomeSound.entries.indexOf(
                    currentMetronome.subDivSound
                )
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "Beat sound", color = Color.Gray,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = "Tick sound", color = Color.Gray,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = "Sub div. sound",
                    color = Color.Gray,
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    modifier = Modifier.padding(10.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                SelectionList(
                    items = list,
                    selectedIndex = currentBeat,
                    modifier = Modifier,
                    onSelected = {
                        currentBeat = it
                        currentMetronome.setBeatSound(MetronomeSound.entries[it])
                    })



                SelectionList(
                    items = list,
                    selectedIndex = currentTick,
                    modifier = Modifier,
                    onSelected = {
                        currentTick = it
                        currentMetronome.setTickSound(MetronomeSound.entries[it])
                    })



                SelectionList(
                    items = list,
                    selectedIndex = currentSubDiv,
                    modifier = Modifier,
                    onSelected = {
                        currentSubDiv = it
                        currentMetronome.setSubDivSound(MetronomeSound.entries[it])
                    })

            }
        }
    }
}
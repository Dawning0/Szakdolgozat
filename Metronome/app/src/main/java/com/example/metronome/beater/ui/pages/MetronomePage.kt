package beater.ui.pages

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import beater.BeaterApp.Companion.app
import beater.metronome.TapTempo
import beater.metronome.TimeSignature
import beater.ui.TimeSignatureSelector
import beater.ui.components.VisualMetronomeComponent

val tapTempo by lazy { TapTempo(app.metronomeManager) }


@SuppressLint("NewApi")
/**
 * A metronóm oldal megvalósítása, amely számos vezérlőelemet és vizuális elemet tartalmaz a metronóm működtetéséhez és testreszabásához.
 *
 * @param modifier A `Modifier`, amely a komponens elrendezését és megjelenését szabályozza.
 */
@Composable
fun MetronomePage(modifier: Modifier = Modifier) {
    val manager = app.metronomeManager
    val running = manager.running.collectAsState().value
    val tempo = manager.tempo.collectAsState().value
    val ts = manager.timeSignature.collectAsState().value
    val subDiv = manager.subDiv.collectAsState().value
    val sd = if (subDiv) "ON" else "OFF"
    val orientation = LocalConfiguration.current.orientation
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val dynamicPadding = (screenWidth / 20).coerceIn(8.dp, 32.dp)
    // Főoszlop az oldal tartalmának megjelenítésére.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp)
            .background(Color(0xff121212)),
        verticalArrangement = Arrangement.spacedBy(screenWidth / 20)
    ) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Tájkép elrendezés (Landscape mode).
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .height(80.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {}
        } else {
            // Portré elrendezés (Portrait mode)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp)
                    .background(Color(0xff111113))
                    .height(220.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Első oszlop: Ütemmutató és alosztás.
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(80.dp)
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(Color(0xff1e1f22), RoundedCornerShape(20.dp))
                                .border(2.dp, Color.Cyan, RoundedCornerShape(20.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "T.S",
                                        color = Color.Gray,
                                        fontSize = TextUnit(14f, TextUnitType.Sp)
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = ts, // 4/1
                                        fontSize = TextUnit(16f, TextUnitType.Sp),
                                        color = Color.White
                                    )
                                }
                                HorizontalDivider(color = Color.Cyan)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "SUB DIV.",
                                        color = Color.Gray,
                                        fontSize = TextUnit(14f, TextUnitType.Sp)
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            manager.setSubDivisions(!subDiv)
                                            manager.refresh()
                                        },
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = sd, // OFF or ON
                                        fontSize = TextUnit(16f, TextUnitType.Sp),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width((screenWidth /40)))

                    // Második oszlop: BPM kijelző és vizuális metronóm.
                    Column(
                        modifier = Modifier
                            .weight(3f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "BPM",
                            color = Color.Gray,
                            fontSize = TextUnit(15f, TextUnitType.Sp)
                        )
                        Text(
                            text = tempo.toString(),
                            fontWeight = FontWeight.Bold,
                            color = Color.Cyan,
                            fontSize = TextUnit(100f, TextUnitType.Sp)
                        )
                        VisualMetronomeComponent(
                            metronomeManager = manager,
                            metronomeColor = Color(0xff1f1d20),
                            filledColor = Color.Cyan,
                            size = 30.dp
                        )
                    }

                    Spacer(modifier = Modifier.width((screenWidth /40)))

                    // Harmadik oszlop: "Tap" gomb a tempó manuális beállításához.
                    Column (
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { tapTempo.tap() },
                            modifier =  Modifier
                                .padding((screenWidth / 240))
                                .fillMaxHeight(0.8f)
                                .width((screenWidth).coerceIn(140.dp, 200.dp))
                                .background(Color(0xff1e1f22), RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color(0xff1e1f22),
                                containerColor = Color.Cyan
                            )
                        ) {
                            Text(
                                text = "TAP",
                                fontSize = TextUnit(14f, TextUnitType.Sp)
                                )
                        }
                    }
                }
            }
        }

        // Tempóállítás gombok.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy((screenWidth / 30), Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    manager.setTempo(tempo - 10)
                    manager.refresh()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff1e1f22),
                    contentColor = Color.Cyan
                )
            ) {
                Text(text = "-10", fontSize = TextUnit(16f, TextUnitType.Sp))
            }
            Button(
                onClick = {
                    manager.setTempo(tempo - 1)
                    manager.refresh()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff1e1f22),
                    contentColor = Color.Cyan
                )
            ) {
                Text(text = "-1", fontSize = TextUnit(16f, TextUnitType.Sp))
            }
            Button(
                onClick = {
                    manager.setTempo(tempo + 1)
                    manager.refresh()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff1e1f22),
                    contentColor = Color.Cyan
                )
            ) {
                Text(text = "+1", fontSize = TextUnit(16f, TextUnitType.Sp))
            }
            Button(
                onClick = {
                    manager.setTempo(tempo + 10)
                    manager.refresh()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff1e1f22),
                    contentColor = Color.Cyan
                )
            ) {
                Text(text = "+10", fontSize = TextUnit(16f, TextUnitType.Sp))
            }
        }
        // Hangerőcsúszka (Volume Slider)
        var sliderPosition by remember { mutableFloatStateOf(manager.currentVolume.toFloat()) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dynamicPadding),
            horizontalArrangement = Arrangement.Center
        ) {
            Column {
                Text("Volume", color = Color.Gray)
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        manager.setVolume(it.toInt())
                    },
                    colors = SliderDefaults.colors(
                        activeTrackColor = Color.Cyan,
                        thumbColor = Color.Cyan,
                        inactiveTrackColor = Color.Gray
                    ),
                    valueRange = 1f..100f
                )
            }
        }
        // Start/Stop gomb és ütemmutató választó.
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            )
        {
            TimeSignatureSelector(
                initialNumerator = 4,
                initialDenominator = 1,
                onTimeSignatureChanged = { numerator, subdiv ->
                    val timeSignature = TimeSignature(numerator, subdiv)
                    manager.setTimeSignature(timeSignature)
                }
            )

            Spacer(modifier = Modifier.width((screenWidth /20)))

            Button(
                modifier = Modifier.size((screenWidth / 5).coerceIn(150.dp, 200.dp)),
                colors = ButtonColors(Color.DarkGray, Color.Cyan,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray),
                onClick = {
                    if (running) manager.stop() else manager.start()
                }
            ) {
                if (running) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Stop")
                } else {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start")
                }
            }
        }
    }
}

/**
 * Egy szöveges komponens a dal címének megjelenítésére.
 *
 * @param text A megjelenítendő szöveg, amely a dal címe.
 */
@Composable
fun SongTitle(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = TextUnit(40f, TextUnitType.Sp)
    )
}
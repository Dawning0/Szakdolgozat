package com.example.metronome.ui.components

import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.metronome.beater.metronome.MetronomeManager
import com.example.metronome.beater.metronome.VisualMetronome

/**
 * Egy vizuális metronómot megjelenítő Compose-komponens.
 * A metronóm az aktuális állapottól függően változó színű és méretű jelzőelemeket jelenít meg.
 *
 * @param metronomeManager A metronóm vezérléséért felelős `MetronomeManager` példány.
 * @param metronomeColor Az alapértelmezett szín a metronóm jelzőihez.
 * @param filledColor A metronóm aktív jelzőinek színe.
 * @param modifier A `Modifier`, amely a komponens elrendezését és megjelenését szabályozza.
 * @param size Az egyes jelzők mérete.
 */
@Composable
fun VisualMetronomeComponent(
    metronomeManager: MetronomeManager,
    metronomeColor: Color,
    filledColor: Color,
    modifier: Modifier = Modifier,
    size: Dp
) {
    val state = metronomeManager.visualMetronome.state.collectAsState().value

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
        for (i in state.indices) {
            val small = (metronomeManager.visualMetronome.subDiv && (i % 2 != 0))
            val glowColor = if (state[i] == VisualMetronome.lightFull || state[i] == VisualMetronome.lightFill) filledColor else Color.Transparent
            val bgColor = if (state[i] == VisualMetronome.lightFull || state[i] == VisualMetronome.lightFill) filledColor else metronomeColor

            GlowingCard(glowingColor = glowColor, modifier = Modifier.size(if (small) size / 2 else size), cornerRadius = 0.dp, containerColor = bgColor, glowingRadius = 20.dp) {

            }
        }
    }
}
/**
 * Egy kártyakomponens, amely fénylő árnyékeffektussal rendelkezik.
 *
 * @param modifier A `Modifier`, amely a kártya elrendezését és megjelenését szabályozza.
 * @param glowingColor A fénylés színe.
 * @param containerColor A kártya alapszíne.
 * @param cornerRadius A kártya sarkainak lekerekítése.
 * @param glowingRadius A fénylő effektus sugara.
 * @param xShifting A fénylő effektus vízszintes eltolása.
 * @param yShifting A fénylő effektus függőleges eltolása.
 * @param content A kártyán belüli tartalom.
 */
@Composable
fun GlowingCard(
    modifier: Modifier = Modifier,
    glowingColor: Color,
    containerColor: Color = Color.White,
    cornerRadius: Dp = 0.dp,
    glowingRadius: Dp = 20.dp,
    xShifting: Dp = 0.dp,
    yShifting: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier.drawBehind {
            val size = this.size
            drawContext.canvas.nativeCanvas.apply {
                drawRoundRect(
                    0f, 0f, size.width, size.height, cornerRadius.toPx(), cornerRadius.toPx(), Paint().apply {
                        color = containerColor.toArgb()
                        setShadowLayer(
                            glowingRadius.toPx(),
                            xShifting.toPx(), yShifting.toPx(),
                            glowingColor.toArgb()
                        )
                    }
                )
            }
        }
    ) {
        content()
    }
}

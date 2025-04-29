package hu.gigsystem.beater.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
/**
 * Egy szövegstílus-készlet a Material 3 tipográfiai stílusainak alkalmazásához.
 * Az alapértelmezett stílusok testreszabhatók és bővíthetők az alkalmazás igényei szerint.
 *
 * @property bodyLarge Egy alapértelmezett szövegstílus nagyobb szövegtörzshöz,
 * amely a következő tulajdonságokkal rendelkezik:
 * - Alapértelmezett betűcsalád: [FontFamily.Default]
 * - Normál betűsúly: [FontWeight.Normal]
 * - Betűméret: 16 sp
 * - Sormagasság: 24 sp
 * - Betűköz: 0.5 sp
 */
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Egyéb alapértelmezett szövegstílusok felülbírálhatók, például:
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
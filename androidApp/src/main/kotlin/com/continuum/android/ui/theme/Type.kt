package com.continuum.android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.continuum.android.R

val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val OutfitFont = GoogleFont("Outfit")
val DmSansFont = GoogleFont("DM Sans")

val OutfitFamily = FontFamily(
    Font(googleFont = OutfitFont, fontProvider = fontProvider, weight = FontWeight.Normal),
    Font(googleFont = OutfitFont, fontProvider = fontProvider, weight = FontWeight.Medium),
    Font(googleFont = OutfitFont, fontProvider = fontProvider, weight = FontWeight.SemiBold),
    Font(googleFont = OutfitFont, fontProvider = fontProvider, weight = FontWeight.Bold),
    Font(googleFont = OutfitFont, fontProvider = fontProvider, weight = FontWeight.ExtraBold),
    Font(googleFont = OutfitFont, fontProvider = fontProvider, weight = FontWeight.Black),
)

val DmSansFamily = FontFamily(
    Font(googleFont = DmSansFont, fontProvider = fontProvider, weight = FontWeight.Normal),
    Font(googleFont = DmSansFont, fontProvider = fontProvider, weight = FontWeight.Medium),
    Font(googleFont = DmSansFont, fontProvider = fontProvider, weight = FontWeight.SemiBold),
)

val ContinuumTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = OutfitFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 34.sp,
        letterSpacing = (-0.04).sp
    ),
    displayMedium = TextStyle(
        fontFamily = OutfitFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        letterSpacing = (-0.02).sp
    ),
    displaySmall = TextStyle(
        fontFamily = OutfitFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        letterSpacing = (-0.02).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = OutfitFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        letterSpacing = (-0.02).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = OutfitFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = OutfitFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = OutfitFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp
    ),
    titleMedium = TextStyle(
        fontFamily = OutfitFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = OutfitFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = DmSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = DmSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = DmSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.5.sp,
        lineHeight = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = DmSansFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    ),
    labelMedium = TextStyle(
        fontFamily = DmSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    ),
    labelSmall = TextStyle(
        fontFamily = DmSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    ),
)

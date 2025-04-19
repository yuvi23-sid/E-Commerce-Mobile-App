package com.example.homepage.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.homepage.R

val Poppins = FontFamily(
    Font(R.font.poppins_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 36.sp),
    headlineMedium = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 24.sp),
    titleMedium = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 20.sp),
    bodyMedium = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Normal, fontSize = 16.sp),
    labelMedium = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Medium, fontSize = 14.sp),
    bodySmall = TextStyle(fontFamily = Poppins, fontWeight = FontWeight.Normal, fontSize = 12.sp)
)
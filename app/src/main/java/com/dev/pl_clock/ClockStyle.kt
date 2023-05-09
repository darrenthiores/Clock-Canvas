package com.dev.pl_clock

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ClockStyle(
    val minuteLineLength: Dp = 5.dp,
    val hourLineLength: Dp = 8.dp,
    val minuteLineColor: Color = Color.LightGray,
    val hourLineColor: Color = Color.Black,
    val secondIndicatorColor: Color = Color.Red,
    val minuteIndicatorColor: Color = Color.Black,
    val hourIndicatorColor: Color = Color.Black,
    val radius: Dp = 100.dp
)

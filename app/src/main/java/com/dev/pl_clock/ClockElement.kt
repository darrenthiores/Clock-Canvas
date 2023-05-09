package com.dev.pl_clock

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

// my solution
@Composable
fun Clock(
    modifier: Modifier,
    style: ClockStyle = ClockStyle(),
    currentSecond: Long = ((System.currentTimeMillis()/1000) % (24*3600))
) {
    val radius = style.radius

    val second = remember {
        mutableStateOf(currentSecond)
    }

    LaunchedEffect(second.value) {
        delay(1000)
        second.value++

        Log.d("SECOND", "${second.value}")
    }

    Canvas(
        modifier = modifier
    ) {
        drawContext.canvas.nativeCanvas.apply {

            drawCircle(
                center.x,
                center.y,
                radius.toPx(),
                Paint().apply {
                    color = android.graphics.Color.WHITE
                    setShadowLayer(
                        60f,
                        0f,
                        0f,
                        android.graphics.Color.argb(50, 0, 0, 0)
                    )
                }
            )

        }

        for(i in 1..60) {
            val angleInRad = (i*6 - 90) * (PI /180f).toFloat()

            val lineType = when {
                i % 5 == 0 -> LineType.Hour
                else -> LineType.Minute
            }

            val lineLength = when(lineType) {
                LineType.Minute -> style.minuteLineLength.toPx()
                LineType.Hour -> style.hourLineLength.toPx()
            }
            val lineColor = when(lineType) {
                LineType.Minute -> style.minuteLineColor
                LineType.Hour -> style.hourLineColor
            }

            val lineStart = Offset(
                x = (radius.toPx() - lineLength) * cos(angleInRad) + center.x,
                y = (radius.toPx() - lineLength) * sin(angleInRad) + center.y
            )

            val lineEnd = Offset(
                x = radius.toPx() * cos(angleInRad) + center.x,
                y = radius.toPx() * sin(angleInRad) + center.y
            )

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )
        }

        // second
        val secondAngleInRad = (second.value*6 - 90) * (PI /180f).toFloat()

        val secondLineEnd = Offset(
            x = (radius.toPx() - style.minuteLineLength.toPx()) * cos(secondAngleInRad) + center.x,
            y = (radius.toPx() - style.minuteLineLength.toPx()) * sin(secondAngleInRad) + center.y
        )

        drawLine(
            color = style.secondIndicatorColor,
            start = Offset(
                x = center.x,
                y = center.y
            ),
            end = secondLineEnd,
            strokeWidth = 1.dp.toPx()
        )

        // minute
        val minuteAngleInRad = (second.value/10 - 90) * (PI /180f).toFloat()

        val minuteLineEnd = Offset(
            x = (radius.toPx() - style.minuteLineLength.toPx()) * cos(minuteAngleInRad) + center.x,
            y = (radius.toPx() - style.minuteLineLength.toPx()) * sin(minuteAngleInRad) + center.y
        )

        drawLine(
            color = style.minuteIndicatorColor,
            start = Offset(
                x = center.x,
                y = center.y
            ),
            end = minuteLineEnd,
            strokeWidth = 2.dp.toPx()
        )

        // hour
        val hourAngleInRad = (second.value/1125 - 90) * (PI /180f).toFloat()

        val hourLineEnd = Offset(
            x = (radius.toPx() - style.hourLineLength.toPx()) * cos(hourAngleInRad) + center.x,
            y = (radius.toPx() - style.hourLineLength.toPx()) * sin(hourAngleInRad) + center.y
        )

        drawLine(
            color = style.minuteIndicatorColor,
            start = Offset(
                x = center.x,
                y = center.y
            ),
            end = hourLineEnd,
            strokeWidth = 5.dp.toPx()
        )
    }
}

// pl solution
@Composable
fun Clock(
    seconds: Float = 0f,
    minutes: Float = 0f,
    hours: Float = 0f,
    radius: Dp = 100.dp
) {
    Canvas(modifier = Modifier.size(radius * 2f)) {
        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                center.x,
                center.y,
                radius.toPx(),
                Paint().apply {
                    color = android.graphics.Color.WHITE
                    setShadowLayer(
                        50f,
                        0f,
                        0f,
                        android.graphics.Color.argb(50, 0, 0, 0)
                    )
                }
            )
        }

        // Lines
        for (i in 0..59) {
            val angleInRad = i * (360f / 60f) * (PI.toFloat() / 180f)
            val lineLength = if (i % 5 == 0) 20.dp.toPx() else 15.dp.toPx()
            val strokeWidth = if (i % 5 == 0) 1.dp.toPx() else 0.5.dp.toPx()
            val color = if(i % 5 == 0) androidx.compose.ui.graphics.Color.DarkGray else Color(0xFF606060)

            val lineStart = Offset(
                x = radius.toPx() * cos(angleInRad) + center.x,
                y = radius.toPx() * sin(angleInRad) + center.y
            )
            val lineEnd = Offset(
                x = (radius.toPx() - lineLength) * cos(angleInRad) + center.x,
                y = (radius.toPx() - lineLength) * sin(angleInRad) + center.y
            )
            drawLine(
                color = color,
                start = lineStart,
                end = lineEnd,
                strokeWidth = strokeWidth
            )
        }

        // Seconds
        rotate(degrees = seconds * (360f / 60f)) {
            drawLine(
                color = androidx.compose.ui.graphics.Color.Red,
                start = center,
                end = Offset(center.x, 20.dp.toPx()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
        // Minutes
        rotate(degrees = minutes * (360f / 60f)) {
            drawLine(
                color = androidx.compose.ui.graphics.Color.Black,
                start = center,
                end = Offset(center.x, 20.dp.toPx()),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
        // Hours
        rotate(degrees = hours * (360f / 12f)) {
            drawLine(
                color = androidx.compose.ui.graphics.Color.Black,
                start = center,
                end = Offset(center.x, 35.dp.toPx()),
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}
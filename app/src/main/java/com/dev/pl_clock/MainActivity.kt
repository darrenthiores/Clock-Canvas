package com.dev.pl_clock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dev.pl_clock.ui.theme.PL_ClockTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PL_ClockTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // my solution
//                    Clock(
//                        modifier = Modifier
//                            .fillMaxSize()
//                    )

                    // pl solution
                    PLClock()
                }
            }
        }
    }
}

@Composable
fun PLClock() {
    val milliseconds = remember {
        System.currentTimeMillis()
    }
    var seconds by remember {
        mutableStateOf((milliseconds / 1000f) % 60f)
    }
    var minutes by remember {
        mutableStateOf(((milliseconds / 1000f) / 60) % 60f)
    }
    var hours by remember {
        mutableStateOf((milliseconds / 1000f) / 3600f + 7f) // + 7f is different depends on the timezone
    }
    LaunchedEffect(key1 = seconds) {
        delay(1000L)
        minutes += 1f / 60f
        hours += 1f / (60f * 12f)
        seconds += 1f
    }
    Clock(
        seconds = seconds,
        minutes = minutes,
        hours = hours
    )
}
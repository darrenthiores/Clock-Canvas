package com.dev.pl_clock

sealed class LineType{
    object Minute: LineType()
    object Hour: LineType()
}

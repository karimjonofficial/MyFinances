package com.orka.myfinances.format

import androidx.compose.runtime.staticCompositionLocalOf

val LocalFormatter = staticCompositionLocalOf<Formatter> {
    error("No Formatter provided")
}

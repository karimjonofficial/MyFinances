package com.orka.myfinances.lib.data

import kotlin.time.Clock
import kotlin.time.Instant

fun now(): Instant {
    return Clock.System.now()
}
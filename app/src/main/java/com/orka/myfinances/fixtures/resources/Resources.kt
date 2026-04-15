package com.orka.myfinances.fixtures.resources

import kotlin.time.Instant

const val description = "Lorem ipsum dolor"
const val productName1 = "Classic Canvas Sneakers"
const val productName2 = "iPhone 17 Pro Max"
const val price = 1000
const val salePrice = 1100
const val amount = 100

val dateTime = Instant.parse("2024-01-01T12:00:00Z")

object Types {
    const val TEXT = "text"
    const val NUMBER = "number"
    const val BOOLEAN = "boolean"
    const val RANGE = "range"

    val all = listOf(TEXT, NUMBER, BOOLEAN, RANGE)
}
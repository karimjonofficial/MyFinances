package com.orka.myfinances.lib.extensions.models

import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.testLib.amount
import com.orka.myfinances.testLib.description
import com.orka.myfinances.testLib.price
import com.orka.myfinances.testLib.product1
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class IsEmptyTest {
    @Test
    fun `Returns true when no description`() {
        assertTrue { Basket().isEmpty() }
    }

    @Test
    fun `Returns false when no description`() {
        assertFalse { Basket(description = description).isEmpty() }
    }

    @Test
    fun `Returns false when price is not 0`() {
        assertFalse { Basket(price = price).isEmpty() }
    }

    @Test
    fun `Returns false when items are not empty`() {
        assertFalse { Basket(items = listOf(BasketItem(product1, amount))).isEmpty() }
    }
}
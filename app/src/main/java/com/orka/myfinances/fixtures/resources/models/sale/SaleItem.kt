package com.orka.myfinances.fixtures.resources.models.sale

import com.orka.myfinances.fixtures.resources.amount
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.id2
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.product.product2
import com.orka.myfinances.data.models.sale.SaleItem

val saleItem1 = SaleItem(id1, product1, amount)
val saleItem2 = SaleItem(id2, product2, amount)

val saleItems = listOf(saleItem1, saleItem2)
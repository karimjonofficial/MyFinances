package com.orka.myfinances.testFixtures.resources.models.stock

import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.testFixtures.resources.dateTime
import com.orka.myfinances.testFixtures.resources.description
import com.orka.myfinances.testFixtures.resources.models.office
import com.orka.myfinances.testFixtures.resources.models.id1
import com.orka.myfinances.testFixtures.resources.models.product.product1
import com.orka.myfinances.testFixtures.resources.price
import com.orka.myfinances.testFixtures.resources.salePrice

val stockItem = StockItem(
    id = id1,
    product = product1,
    amount = 100,
    office = office,
    dateTime = dateTime,
    comment = description,
)
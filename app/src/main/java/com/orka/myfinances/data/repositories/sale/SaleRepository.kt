package com.orka.myfinances.data.repositories.sale

import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.sale.SaleItem
import com.orka.myfinances.data.repositories.Item
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.client1
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.product1
import com.orka.myfinances.fixtures.resources.models.sale.sales
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.fixtures.data.repositories.MockRepository
import kotlin.time.ExperimentalTime

class SaleRepository : MockRepository<Sale, AddSaleRequest>(items = sales) {
    override fun acceptable(request: AddSaleRequest): Boolean {
        return request.client > 0
    }

    override fun map(request: AddSaleRequest): Sale {
        return request.toSale()
    }

    @OptIn(ExperimentalTime::class)
    private fun AddSaleRequest.toSale(): Sale {
        return Sale(
            id = id1,
            client = client1,
            user = user1,
            price = price,
            dateTime = dateTime,
            items = items.map { it.toSaleItem() },
            description = description
        )
    }

    private fun Item.toSaleItem(): SaleItem {
        return SaleItem(
            id = id1,
            product = product1,
            amount = amount
        )
    }
}
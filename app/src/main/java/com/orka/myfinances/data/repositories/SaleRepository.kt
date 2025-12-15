package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.client1
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.data.Repository
import com.orka.myfinances.lib.extensions.models.toSaleItem
import kotlinx.coroutines.delay
import kotlin.time.ExperimentalTime

class SaleRepository : Repository<Sale> {
    private val sales = com.orka.myfinances.fixtures.resources.models.sale.sales.toMutableList()

    override suspend fun get(): List<Sale> {
        delay(1000)
        return sales.toList()
    }

    suspend fun add(request: AddSaleRequest): Sale? {
        delay(1000)
        if(request.client > 0) {
            val sale = request.toSale()
            sales.add(sale)
            return sale
        }
        return null
    }

    @OptIn(ExperimentalTime::class)
    private fun AddSaleRequest.toSale(): Sale {
        return Sale(
            id = id1,
            client = client1,
            user = user1,
            price = basket.price,
            dateTime = dateTime,
            items = basket.items.map { it.toSaleItem(id1) },
            description = basket.description
        )
    }
}
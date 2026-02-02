package com.orka.myfinances.data.repositories.sale

import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.sale.SaleItem
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.client1
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.office1
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.sale.sales
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.data.models.Item
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class SaleRepository : MockGetRepository<Sale>, MockAddRepository<Sale, AddSaleRequest> {
    private val flow = MutableSharedFlow<SaleEvent>()
    val events: Flow<SaleEvent> = flow

    override val items = sales.toMutableList()

    override suspend fun AddSaleRequest.map(): Sale {
        return Sale(
            id = id1,
            client = client1,
            user = user1,
            price = price,
            dateTime = dateTime,
            items = items.map { it.toSaleItem() },
            office = office1,
            description = description
        )
    }

    override suspend fun afterAdd(item: Sale) {
        flow.emit(SaleEvent)
    }

    private fun Item.toSaleItem(): SaleItem {
        return SaleItem(
            id = id1,
            product = product1,
            amount = amount
        )
    }
}
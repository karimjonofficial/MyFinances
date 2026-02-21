package com.orka.myfinances.data.repositories.sale

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.sale.SaleItem
import com.orka.myfinances.fixtures.resources.models.office1
import com.orka.myfinances.fixtures.resources.models.sale.sales
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.data.models.Item
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.data.repositories.Generator
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class SaleRepository(
    private val generator: Generator<Id>,
    private val getProductById: GetById<Product>,
    private val getClientById: GetById<Client>,
) : MockGetRepository<Sale>,
    MockAddRepository<Sale, AddSaleRequest> {
    private val flow = MutableSharedFlow<SaleEvent>()
    val events: Flow<SaleEvent> = flow

    override val items = sales.toMutableList()

    override suspend fun AddSaleRequest.map(): Sale {
        return Sale(
            id = generator.generate(),
            client = getClientById.getById(clientId)!!,
            user = user1,
            price = price,
            dateTime = now(),
            items = items.map { it.toSaleItem() },
            office = office1,
            description = description
        )
    }

    override suspend fun afterAdd(item: Sale) {
        flow.emit(SaleEvent)
    }

    private suspend fun Item.toSaleItem(): SaleItem {
        return SaleItem(
            id = generator.generate(),
            product = getProductById.getById(id)!!,
            amount = amount
        )
    }
}
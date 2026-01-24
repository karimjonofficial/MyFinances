package com.orka.myfinances.data.repositories.stock

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.fixtures.resources.models.stockItems
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository

class StockRepository : MockGetRepository<StockItem>, MockGetByIdRepository<StockItem>,
    MockGetByParameterRepository<StockItem, Category> {
    override val items = stockItems.toMutableList()

    override suspend fun List<StockItem>.filter(parameter: Category): List<StockItem>? {
        return filter { it.product.title.category == parameter }.ifEmpty { null }
    }

    override suspend fun List<StockItem>.find(id: Id): StockItem? {
        return find { it.id == id }
    }
}
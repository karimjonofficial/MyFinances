package com.orka.myfinances.data.repositories.stock

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.AddProductRequest
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.stockItems
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class StockRepository(
    private val productRepository: Add<Product, AddProductRequest>,
    private val office: Office
) : MockGetRepository<StockItem>, MockGetByIdRepository<StockItem>,
    MockGetByParameterRepository<StockItem, Category>,
    MockAddRepository<StockItem, AddStockItemRequest> {
    private val flow = MutableSharedFlow<StockRepositoryEvent>()
    val events: Flow<StockRepositoryEvent> = flow

    override val items = stockItems.toMutableList()

    override suspend fun AddStockItemRequest.map(): StockItem {
        val product = productRepository.add(
            AddProductRequest(
                titleId = productTitleId,
                price = price,
                salePrice = salePrice
            )
        ) ?: throw Exception("Product not found")
        return StockItem(
            id = id1,
            product = product,
            amount = amount,
            office = office,
            dateTime = now(),
            comment = comment
        )
    }

    override suspend fun afterAdd(item: StockItem) {
        flow.emit(StockRepositoryEvent(item.product.title.category))
    }

    override suspend fun List<StockItem>.filter(parameter: Category): List<StockItem> {
        return filter { it.product.title.category == parameter }
    }

    override suspend fun List<StockItem>.find(id: Id): StockItem? {
        return find { it.id == id }
    }
}
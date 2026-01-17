package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.product.products
import com.orka.myfinances.lib.data.repositories.GetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ProductRepository(
    private val repository: ProductTitleRepository
) : MockGetRepository<Product>(items = products), GetByIdRepository<Product> {
    private val events = MutableSharedFlow<ProductRepositoryEvent>(replay = 1)
    fun events(): Flow<ProductRepositoryEvent> = events

    suspend fun add(request: AddProductRequest): Product? {
        val title = repository.getById(request.titleId)
        return if (title == null) null else {
            val p = Product(
                id = id1,
                title = title,
                price = request.price,
                salePrice = request.salePrice,
                dateTime = dateTime,
                description = request.description
            )
            items.add(p)
            events.emit(ProductRepositoryEvent.Add(title.category.id))
            p
        }
    }

    suspend fun getByCategory(id: Id): List<Product>? {
        delay(duration)
        val list = items.filter { it.title.category.id == id }
        return list.ifEmpty { null }
    }

    override suspend fun getById(id: Id): Product? {
        delay(duration)
        return items.find { it.id == id }
    }
}
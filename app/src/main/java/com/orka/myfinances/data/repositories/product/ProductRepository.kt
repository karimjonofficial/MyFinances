package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.product.products
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ProductRepository(private val repository: ProductTitleRepository) :
    MockGetRepository<Product>, MockGetByIdRepository<Product>,
    MockGetByParameterRepository<Product, Category>,
    MockAddRepository<Product, AddProductRequest> {
    override val items = products.toMutableList()

    private val flow = MutableSharedFlow<ProductRepositoryEvent>()
    val events: Flow<ProductRepositoryEvent> = flow as Flow<ProductRepositoryEvent>

    override suspend fun AddProductRequest.map(): Product {
        val title = repository.getById(titleId)!!
        flow.emit(ProductRepositoryEvent.Add(title.category.id))

        return Product(
            id = id1,
            title = title,
            price = price,
            salePrice = salePrice,
            dateTime = dateTime,
            description = description
        )
    }

    override suspend fun acceptable(request: AddProductRequest): Boolean {
        val title = repository.getById(request.titleId)
        return title != null
    }

    override suspend fun List<Product>.filter(parameter: Category): List<Product>? {
        val list = items.filter { it.title.category == parameter }
        return list.ifEmpty { null }
    }

    override suspend fun List<Product>.find(id: Id): Product? {
        return items.find { it.id == id }
    }
}
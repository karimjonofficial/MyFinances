package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.fixtures.resources.models.product.products
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.data.repositories.Generator
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository

class ProductRepository(
    private val titleRepository: GetById<ProductTitle>,
    private val generator: Generator<Id>,
) : MockGetRepository<Product>, MockGetByIdRepository<Product>,
    MockGetByParameterRepository<Product, Category>,
    MockAddRepository<Product, AddProductRequest> {
    override val items = products.toMutableList()

    override suspend fun AddProductRequest.map(): Product {
        val title = titleRepository.getById(titleId)!!
        return Product(
            id = generator.generate(),
            title = title,
            price = price,
            salePrice = salePrice,
            dateTime = now(),
        )
    }

    override suspend fun List<Product>.filter(parameter: Category): List<Product> {
        val list = items.filter { it.title.category == parameter }
        return list
    }

    override suspend fun List<Product>.find(id: Id): Product? {
        return items.find { it.id == id }
    }
}
package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.product.products
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository

class ProductRepository(private val titleRepository: GetById<ProductTitle>) :
    MockGetRepository<Product>, MockGetByIdRepository<Product>,
    MockGetByParameterRepository<Product, Category>,
    MockAddRepository<Product, AddProductRequest> {
    override val items = products.toMutableList()

    override suspend fun AddProductRequest.map(): Product {
        val title = titleRepository.getById(titleId)!!
        return Product(
            id = id1,
            title = title,
            price = price,
            salePrice = salePrice,
            dateTime = dateTime,
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
package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.fixtures.resources.models.folder.folder2
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.product1
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ProductApiServiceImpl : ProductApiService {
    override suspend fun add(request: AddProductRequest): Product {
        return request.toProduct(id1)
    }

    override suspend fun get(): List<Product> {
        return listOf(product1)
    }

    override suspend fun get(id: Int): List<Product> {
        return listOf(product1)
    }

    override suspend fun getById(id: Int): Product {
        return product1
    }

    @OptIn(ExperimentalTime::class)
    fun AddProductRequest.toProduct(id: Id): Product {
        return Product(
            id = id,
            title = ProductTitle(id, name, description),
            price = price,
            salePrice = salePrice,
            warehouse = folder2,
            properties = emptyList(),
            dateTime = Clock.System.now(),
            description = description
        )
    }
}
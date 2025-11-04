package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.fixtures.resources.models.folder.folder2
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.product1

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
}

fun AddProductRequest.toProduct(id: Id): Product {
    return Product(
        id = id,
        name = name,
        price = price,
        salePrice = salePrice,
        warehouse = folder2,
        properties = emptyList(),
        description = description
    )
}
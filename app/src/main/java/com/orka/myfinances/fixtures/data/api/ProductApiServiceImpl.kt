package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.ProductApiService
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.fixtures.resources.models.folder.folder2
import com.orka.myfinances.fixtures.resources.models.id1

class ProductApiServiceImpl : ProductApiService {
    override suspend fun add(request: AddProductRequest): Product? {
        return request.toProduct(id1)
    }

    override suspend fun get(): List<Product>? {
        return null
    }
}

fun AddProductRequest.toProduct(id: Id): Product {
    return Product(
        id = id,
        name = name,
        price = price.toDouble(),
        salePrice = salePrice.toDouble(),
        warehouse = folder2,
        properties = emptyList(),
        description = description
    )
}
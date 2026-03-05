package com.orka.myfinances.data.repositories.product.title

import com.orka.myfinances.data.api.title.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.fixtures.resources.models.product.productTitles
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.GetByParameter
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ProductTitleRepository(private val client: HttpClient) : MockGetByIdRepository<ProductTitle>,
    GetByParameter<ProductTitle, Category>,
    Add<ProductTitle, AddProductTitleRequest>,
    MockGetRepository<ProductTitle>, SetProductTitlePrice {
    private val flow = MutableSharedFlow<ProductTitleEvent>()
    val events: Flow<ProductTitleEvent> = flow

    override val items = productTitles.toMutableList()

    private suspend fun emit(title: ProductTitle) {
        flow.emit(ProductTitleEvent(title.category.id))
    }

    override suspend fun get(parameter: Category): List<ProductTitle>? {
        val response = client.get(
            urlString = "product-titles/",
            block = { parameter("category", parameter.id.value) }
        )

        if(response.status == HttpStatusCode.OK) {
            val titles = response.body<List<ProductTitleApiModel>>()
            return titles.map { it.map(parameter) }
        } else return null
    }

    override suspend fun add(request: AddProductTitleRequest): ProductTitle? {
        val response = client.post(
            urlString = "product-titles/",
            block = { setBody(request.map()) }
        )
        if(response.status == HttpStatusCode.Created) {
            val title = response.body<ProductTitleApiModel>().map(request.category)
            emit(title)
            return title
        } else return null
    }

    override suspend fun setPrice(id: Id, price: Int, salePrice: Int): ProductTitle {
        delay(duration)
        val index = items.indexOfFirst { it.id == id }
        val t = items[index]
        val new = t.copy(defaultPrice = price, defaultSalePrice = salePrice)
        items[index] = new
        emit(new)
        return new
    }

    override suspend fun List<ProductTitle>.find(id: Id): ProductTitle? {
        return this.find { it.id == id }
    }
}
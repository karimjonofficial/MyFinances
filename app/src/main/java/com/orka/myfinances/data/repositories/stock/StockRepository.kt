package com.orka.myfinances.data.repositories.stock

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.AddProductRequest
import com.orka.myfinances.fixtures.resources.models.stockItems
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Generator
import com.orka.myfinances.lib.data.repositories.GetByParameter
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByParameterRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

class StockRepository(
    private val office: Office,
    private val client: HttpClient,
    private val add: Add<Product, AddProductRequest>,
    private val generator: Generator<Id>,
) : MockGetRepository<StockItem>, MockGetByIdRepository<StockItem>,
    GetByParameter<StockItem, Category>,
    MockAddRepository<StockItem, AddStockItemRequest> {
    private val flow = MutableSharedFlow<StockEvent>()
    val events: Flow<StockEvent> = flow

    override val items = stockItems.toMutableList()

    override suspend fun AddStockItemRequest.map(): StockItem {
        val product = add.add(
            AddProductRequest(
                titleId = productTitleId,
                price = price,
                salePrice = salePrice
            )
        ) ?: throw Exception("Product not found")
        return StockItem(
            id = generator.generate(),
            product = product,
            amount = amount,
            office = office,
            dateTime = now()
        )
    }

    override suspend fun afterAdd(item: StockItem) {
        flow.emit(StockEvent(item.product.title.category))
    }

    override suspend fun List<StockItem>.find(id: Id): StockItem? {
        return find { it.id == id }
    }

    override suspend fun get(parameter: Category): List<StockItem>? {
        val response = client.get(
            urlString = "stock-items",
            block = { parameter("category", parameter.id.value) }
        )

        if(response.status == HttpStatusCode.OK) {
            val titles = response.body<List<StockItemApiModel>>()
            return titles.map { it.map() }
        } else return null
    }
}

@Serializable
data class StockItemApiModel(
    val id: Int,
    @SerialName("product") val productId: Int,
    @SerialName("branch") val officeId: Int,
    val amount: Int,
    @SerialName("date_time") val dateTime: Instant
)

fun StockItemApiModel.map(): StockItem {
    return StockItem(
        id = Id(value = id),
        product = Product(
            id = Id(value = productId),
        ),
        amount = amount,
    )
}
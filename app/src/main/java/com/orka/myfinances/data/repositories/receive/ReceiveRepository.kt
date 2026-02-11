package com.orka.myfinances.data.repositories.receive

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.StockItem
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.models.receive.ReceiveItem
import com.orka.myfinances.data.repositories.product.AddProductRequest
import com.orka.myfinances.data.repositories.product.title.SetProductTitlePrice
import com.orka.myfinances.data.repositories.stock.AddStockItemRequest
import com.orka.myfinances.fixtures.resources.models.office1
import com.orka.myfinances.fixtures.resources.models.receive.receives
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Generator
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ReceiveRepository(
    private val productTitleRepository: GetById<ProductTitle>,
    private val productRepository: Add<Product, AddProductRequest>,
    private val stockRepository: Add<StockItem, AddStockItemRequest>,
    private val setProductTitlePrice: SetProductTitlePrice,
    private val generator: Generator<Id>
) : MockGetRepository<Receive>, MockAddRepository<Receive, AddReceiveRequest> {
    private val flow = MutableSharedFlow<ReceiveEvent>()
    val events: Flow<ReceiveEvent> = flow

    override val items = receives.toMutableList()

    override suspend fun AddReceiveRequest.map(): Receive {
        return Receive(
            id = generator.generate(),
            user = user1,
            office = office1,
            items = items.map {
                val title = productTitleRepository.getById(it.productTitleId)!!
                stockRepository.add(AddStockItemRequest(
                        productTitleId = it.productTitleId,
                        amount = it.amount,
                        price = it.price,
                        salePrice = it.salePrice
                    ))
                if(title.defaultPrice != it.price || title.defaultSalePrice != it.salePrice)
                    setProductTitlePrice.setPrice(title.id, it.price, it.salePrice)
                val product = productRepository.add(AddProductRequest(
                        titleId = it.productTitleId,
                        price = it.price,
                        salePrice = it.salePrice,
                        description = it.description
                    ))!!
                ReceiveItem(generator.generate(), product, it.amount)
            },
            price = price,
            dateTime = now(),
            description = comment
        )
    }

    override suspend fun afterAdd(item: Receive) {
        flow.emit(ReceiveEvent)
    }
}
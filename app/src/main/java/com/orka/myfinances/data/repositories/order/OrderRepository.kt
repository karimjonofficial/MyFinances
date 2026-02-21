package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.order.OrderItem
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.fixtures.resources.models.order.orders
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.data.models.Item
import com.orka.myfinances.lib.data.now
import com.orka.myfinances.lib.data.repositories.Generator
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository

class OrderRepository(
    private val generator: Generator<Id>,
    private val getProductById: GetById<Product>,
    private val getClientById: GetById<Client>
) : MockGetRepository<Order>, MockAddRepository<Order, AddOrderRequest> {
    override val items = orders.toMutableList()

    override suspend fun AddOrderRequest.map(): Order {
        return Order(
            id = generator.generate(),
            user = user1,
            client = getClientById.getById(clientId)!!,
            items = items.map { it.toOrderItem() },
            price = price,
            dateTime = now(),
            endDateTime = endDateTime,
            completed = false,
            description = description
        )
    }

    private suspend fun Item.toOrderItem(): OrderItem {
        return OrderItem(
            id = generator.generate(),
            product = getProductById.getById(id)!!,
            amount = amount
        )
    }
}
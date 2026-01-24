package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.order.OrderItem
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.client1
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.order.orders
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.data.models.Item
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository

class OrderRepository : MockGetRepository<Order>, MockAddRepository<Order, AddOrderRequest> {
    override val items = orders.toMutableList()

    override suspend fun AddOrderRequest.map(): Order {
        return Order(
            id = id1,
            user = user1,
            client = client1,
            items = items.map { it.toOrderItem() },
            price = price,
            dateTime = dateTime,
            endDateTime = dateTime,
            completed = false,
            description = description
        )
    }

    fun Item.toOrderItem(): OrderItem {
        return OrderItem(
            id = id1,
            product = product1,
            amount = amount
        )
    }
}
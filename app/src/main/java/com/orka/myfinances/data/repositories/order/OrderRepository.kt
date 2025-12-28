package com.orka.myfinances.data.repositories.order

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.order.OrderItem
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.client1
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.order.orders
import com.orka.myfinances.fixtures.resources.models.product1
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.data.repositories.Item
import com.orka.myfinances.lib.fixtures.data.repositories.MockRepository

class OrderRepository : MockRepository<Order, AddOrderRequest>(orders) {

    override fun map(request: AddOrderRequest): Order {
        return Order(
            id = id1,
            user = user1,
            client = client1,
            items = request.items.map { it.toOrderItem() },
            price = request.price,
            dateTime = dateTime,
            endDateTime = dateTime,
            completed = false,
            description = request.description
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
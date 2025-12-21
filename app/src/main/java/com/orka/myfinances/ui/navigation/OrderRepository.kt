package com.orka.myfinances.ui.navigation

import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.repositories.GetRepository
import com.orka.myfinances.fixtures.resources.models.order.orders

class OrderRepository : GetRepository<Order>(orders)
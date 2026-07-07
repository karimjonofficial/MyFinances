package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.models.Id
import kotlinx.coroutines.flow.Flow

interface BasketRepository {
    val events: Flow<BasketEvent>
    suspend fun get(): List<MinBasketItem>
    suspend fun add(id: Id, amount: Int)
    suspend fun remove(id: Id, amount: Int)
    suspend fun clear()
}


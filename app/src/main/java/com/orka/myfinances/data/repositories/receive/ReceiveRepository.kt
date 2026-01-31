package com.orka.myfinances.data.repositories.receive

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.models.receive.ReceiveItem
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.office1
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.receive.receives
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlin.time.Clock

class ReceiveRepository : MockGetRepository<Receive>, MockAddRepository<Receive, AddReceiveRequest> {
    override val items = receives.toMutableList()

    override suspend fun AddReceiveRequest.map(): Receive {
        return Receive(
            id = id1,
            user = user1,
            office = office1,
            items = items.map { ReceiveItem(id1, product1, it.amount) },
            price = price,
            dateTime = Clock.System.now(),
            description = description
        )
    }
}
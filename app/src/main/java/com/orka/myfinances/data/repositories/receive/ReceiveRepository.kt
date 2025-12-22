package com.orka.myfinances.data.repositories.receive

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.models.receive.ReceiveItem
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.product1
import com.orka.myfinances.fixtures.resources.models.receive.receives
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.fixtures.data.repositories.MockRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class ReceiveRepository : MockRepository<Receive, AddReceiveRequest>(items = receives) {
    override fun map(request: AddReceiveRequest): Receive {
        return request.toReceive()
    }

    @OptIn(ExperimentalTime::class)
    private fun AddReceiveRequest.toReceive(): Receive {
        return Receive(
            id = id1,
            user = user1,
            items = items.map { ReceiveItem(id1, product1, it.amount) },
            price = price,
            dateTime = Clock.System.now(),
            description = description
        )
    }
}
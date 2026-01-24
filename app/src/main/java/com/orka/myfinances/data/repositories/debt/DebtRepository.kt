package com.orka.myfinances.data.repositories.debt

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.debts
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository

class DebtRepository : MockGetRepository<Debt>, MockAddRepository<Debt, AddDebtRequest> {
    override val items = debts.toMutableList()

    override suspend fun AddDebtRequest.map(): Debt {
        return Debt(
            id = Id(items.size + 1),
            user = user1,
            client = client,
            price = price,
            notified = false,
            dateTime = dateTime,
            endDateTime = endDateTime,
            description = description
        )
    }
}

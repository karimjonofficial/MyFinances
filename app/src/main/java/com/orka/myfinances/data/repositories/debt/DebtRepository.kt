package com.orka.myfinances.data.repositories.debt

import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.fixtures.resources.dateTime
import com.orka.myfinances.fixtures.resources.models.debts
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.fixtures.data.repositories.MockRepository

class DebtRepository : MockRepository<Debt, AddDebtRequest>(debts) {

    override fun map(request: AddDebtRequest): Debt {
        return Debt(
            id = Id(items.size + 1),
            user = user1,
            client = request.client,
            price = request.price,
            notified = false,
            dateTime = dateTime,
            endDateTime = request.endDateTime,
            description = request.description
        )
    }
}

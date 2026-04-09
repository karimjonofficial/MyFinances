package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class DebtApi(
    override val httpClient: HttpClient,
    override val officeId: Id,
    override val baseUrl: String = "debts/"
) : OfficeScopedApi
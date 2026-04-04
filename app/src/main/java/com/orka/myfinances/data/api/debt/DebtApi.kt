package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class DebtApi(
    override val httpClient: HttpClient,
    override val office: Office,
    override val baseUrl: String = "debts/"
) : OfficeScopedApi
package com.orka.myfinances.data.api.order

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class OrderApi(
    override val officeId: Id,
    override val httpClient: HttpClient,
    override val baseUrl: String = "orders/",
) : OfficeScopedApi
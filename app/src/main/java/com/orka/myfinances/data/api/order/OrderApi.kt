package com.orka.myfinances.data.api.order

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class OrderApi(
    override val office: Office,
    override val httpClient: HttpClient,
    override val baseUrl: String = "orders/",
) : OfficeScopedApi
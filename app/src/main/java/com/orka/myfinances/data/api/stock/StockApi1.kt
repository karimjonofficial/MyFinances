package com.orka.myfinances.data.api.stock

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class StockApi1(
    override val office: Office,
    override val httpClient: HttpClient
) : OfficeScopedApi {
    override val baseUrl = "stock-items/"
}

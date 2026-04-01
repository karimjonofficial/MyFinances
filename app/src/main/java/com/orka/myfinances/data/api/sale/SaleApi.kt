package com.orka.myfinances.data.api.sale

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class SaleApi(
    override val office: Office,
    override val httpClient: HttpClient,
    override val baseUrl: String = "sales/"
) : OfficeScopedApi
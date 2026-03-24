package com.orka.myfinances.data.api.title

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class ProductTitleApi1(
    override val office: Office,
    override val httpClient: HttpClient
) : OfficeScopedApi {
    override val baseUrl = "product-titles/"
}
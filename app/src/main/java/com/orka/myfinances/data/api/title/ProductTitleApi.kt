package com.orka.myfinances.data.api.title

import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class ProductTitleApi(
    override val officeId: Id,
    override val httpClient: HttpClient,
    override val baseUrl: String = "product-titles/"
) : OfficeScopedApi<ProductTitleApiModel>
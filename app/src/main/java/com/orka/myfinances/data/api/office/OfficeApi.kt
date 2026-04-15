package com.orka.myfinances.data.api.office

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.scoped.company.CompanyScopedApi
import io.ktor.client.HttpClient

class OfficeApi(
    override val companyId: Id,
    override val httpClient: HttpClient,
    override val baseUrl: String = "branches/"
) : CompanyScopedApi<OfficeApiModel>
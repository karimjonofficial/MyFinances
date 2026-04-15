package com.orka.myfinances.data.api.template

import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class TemplateApi(
    override val httpClient: HttpClient,
    override val officeId: Id,
    override val baseUrl: String = "templates/"
) : OfficeScopedApi<TemplateApiModel>
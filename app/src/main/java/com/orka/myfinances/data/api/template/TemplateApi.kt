package com.orka.myfinances.data.api.template

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient

class TemplateApi(
    override val httpClient: HttpClient,
    override val office: Office,
    override val baseUrl: String = "templates/"
) : OfficeScopedApi
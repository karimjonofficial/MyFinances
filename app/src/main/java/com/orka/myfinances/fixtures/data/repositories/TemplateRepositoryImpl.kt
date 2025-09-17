package com.orka.myfinances.fixtures.data.repositories

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateRepository

class TemplateRepositoryImpl : TemplateRepository {

    override suspend fun add(request: AddTemplateRequest): Template? {
        return null
    }
}
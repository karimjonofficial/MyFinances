package com.orka.myfinances.testFixtures.data.repositories.template

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateRepository

class TemplateRepositoryStub : TemplateRepository {

    override suspend fun add(request: AddTemplateRequest): Template? {
        return null
    }

    override suspend fun get(): List<Template>? {
        return emptyList()
    }
}

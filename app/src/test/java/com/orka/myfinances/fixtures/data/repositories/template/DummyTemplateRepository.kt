package com.orka.myfinances.fixtures.data.repositories.template

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateRepository

class DummyTemplateRepository : TemplateRepository {

    override suspend fun add(request: AddTemplateRequest): Template? {
        return null
    }
}
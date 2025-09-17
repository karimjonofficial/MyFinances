package com.orka.myfinances.data.repositories.template

import com.orka.myfinances.data.models.template.Template

interface TemplateRepository {
    suspend fun add(request: AddTemplateRequest): Template?
}

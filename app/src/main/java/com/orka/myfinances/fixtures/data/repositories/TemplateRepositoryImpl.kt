package com.orka.myfinances.fixtures.data.repositories

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateFieldModel
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.fixtures.resources.models.template.templates
import com.orka.myfinances.fixtures.resources.types
import com.orka.myfinances.lib.extensions.models.toId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TemplateRepositoryImpl : TemplateRepository {
    private val state = templates.toMutableList()

    private val flow = MutableSharedFlow<TemplateRepositoryEvent>()
    val events = flow as Flow<TemplateRepositoryEvent>

    override suspend fun add(request: AddTemplateRequest): Template {
        state.add(request.toTemplate())
        flow.emit(TemplateRepositoryEvent)
        return request.toTemplate()
    }

    override suspend fun get(): List<Template> {
        return state.toList()
    }

    private fun AddTemplateRequest.toTemplate(): Template {
        return Template(
            id = state.size.toId(),
            name = name,
            fields = fields.map { it.toTemplateField() }
        )
    }
    private fun TemplateFieldModel.toTemplateField(): TemplateField {
        return TemplateField(
            id = 1.toId(),
            name = name,
            type = types[typeId]
        )
    }
}
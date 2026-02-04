package com.orka.myfinances.data.repositories.template

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.models.template.templates
import com.orka.myfinances.fixtures.resources.types
import com.orka.myfinances.lib.extensions.models.toId
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TemplateRepository : MockGetRepository<Template>,
    MockAddRepository<Template, AddTemplateRequest>,
    MockGetByIdRepository<Template> {
    override val items = templates.toMutableList()

    override suspend fun List<Template>.find(id: Id): Template? {
        return find { it.id == id }
    }

    val flow = MutableSharedFlow<TemplateEvent>()
    val events = flow as Flow<TemplateEvent>

    override suspend fun AddTemplateRequest.map(): Template {
        flow.emit(TemplateEvent)
        return Template(
            id = id1,
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
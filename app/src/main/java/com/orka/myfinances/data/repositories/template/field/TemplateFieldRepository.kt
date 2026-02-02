package com.orka.myfinances.data.repositories.template.field

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.fixtures.resources.models.template.templateFields
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository

class TemplateFieldRepository : MockGetByIdRepository<TemplateField> {
    override val items = templateFields.toMutableList()

    override suspend fun List<TemplateField>.find(id: Id): TemplateField? {
        return this.find { it.id == id }
    }
}
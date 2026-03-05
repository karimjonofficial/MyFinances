package com.orka.myfinances.data.repositories.template

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.models.template.TemplateField
import com.orka.myfinances.fixtures.resources.models.template.templates
import com.orka.myfinances.lib.data.repositories.Generator
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository
import com.orka.myfinances.ui.screens.templates.add.TemplateFieldModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TemplateRepository(
    private val generator: Generator<Id>,
    private val client: HttpClient
) : MockGetRepository<Template>,
    MockAddRepository<Template, AddTemplateRequest>,
    GetById<Template> {
    override val items = templates.toMutableList()

    override suspend fun getById(id: Id): Template? {
        val response = client.get("templates/${id.value}/")

        if(response.status == HttpStatusCode.OK) {
            val template = response.body<TemplateApiModel>().map()
            return template
        } else return null
    }

    val flow = MutableSharedFlow<TemplateEvent>()
    val events = flow as Flow<TemplateEvent>

    override suspend fun AddTemplateRequest.map(): Template {
        flow.emit(TemplateEvent)
        return Template(
            id = generator.generate(),
            name = name,
            fields = fields.map { it.toTemplateField() }
        )
    }

    private fun TemplateFieldModel.toTemplateField(): TemplateField {
        return TemplateField(
            id = generator.generate(),
            name = name,
            type = type
        )
    }
}
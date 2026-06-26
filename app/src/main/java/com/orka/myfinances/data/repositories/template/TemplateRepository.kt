package com.orka.myfinances.data.repositories.template

import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.template.requests.AddTemplateRequest
import com.orka.myfinances.lib.data.models.toChunk
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.viewmodel.Chunk
import kotlinx.coroutines.flow.MutableSharedFlow

class TemplateRepository(
    private val officeId: Id,
    private val api: TemplateApi,
    private val flow: MutableSharedFlow<TemplateEvent>
) : GetChunk<TemplateDto>, GetById<TemplateDto>, Insert<AddTemplateRequest> {
    override suspend fun getChunk(
        size: Int,
        page: Int,
        query: String?
    ): Chunk<TemplateDto>? {
        return api.getChunk(
            page = page,
            pageSize = size,
            search = query,
            ordering = "name"
        )?.toChunk { it.toDto() }
    }

    override suspend fun getById(id: Id): TemplateDto? {
        return api.getById(id.value)?.toDto()
    }

    override suspend fun insert(request: AddTemplateRequest): Boolean {
        val success = api.insert(request.toApiRequest(officeId))
        if (success)
            flow.emit(TemplateEvent)
        return success
    }
}

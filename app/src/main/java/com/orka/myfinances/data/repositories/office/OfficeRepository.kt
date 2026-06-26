package com.orka.myfinances.data.repositories.office

import com.orka.myfinances.data.api.office.OfficeApi
import com.orka.myfinances.data.dtos.office.OfficeDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.models.toChunk
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.viewmodel.Chunk

class OfficeRepository(
    private val companyId: Id,
    private val api: OfficeApi
) : GetChunk<OfficeDto>, GetById<OfficeDto>, Get<OfficeDto> {
    override suspend fun getChunk(
        size: Int,
        page: Int,
        query: String?
    ): Chunk<OfficeDto>? {
        return api.getChunk(companyId.value, page, size, query)?.toChunk { it.toDto() }
    }

    override suspend fun getById(id: Id): OfficeDto? {
        return api.getById(id.value)?.toDto()
    }

    override suspend fun getAll(search: String?): List<OfficeDto>? {
        return api.get(companyId.value, search)?.map { it.toDto() }
    }
}

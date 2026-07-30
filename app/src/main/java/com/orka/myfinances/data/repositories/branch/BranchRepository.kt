package com.orka.myfinances.data.repositories.branch

import com.orka.myfinances.data.api.branch.BranchApi
import com.orka.myfinances.data.dtos.branch.BranchDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.models.toChunk
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.data.repositories.Chunk

class BranchRepository(
    private val companyId: Id,
    private val api: BranchApi
) : GetChunk<BranchDto>, GetById<BranchDto>, Get<BranchDto> {
    override suspend fun getChunk(
        size: Int,
        page: Int,
        query: String?
    ): Chunk<BranchDto>? {
        return api.getChunk(companyId.value, page, size, query)?.toChunk { it.toDto() }
    }

    override suspend fun getById(id: Id): BranchDto? {
        return api.getById(id.value)?.toDto()
    }

    override suspend fun getAll(search: String?): List<BranchDto>? {
        return api.get(companyId.value, search)?.map { it.toDto() }
    }
}

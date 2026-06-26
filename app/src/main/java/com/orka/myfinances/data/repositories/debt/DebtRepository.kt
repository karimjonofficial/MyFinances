package com.orka.myfinances.data.repositories.debt

import com.orka.myfinances.data.api.debt.DebtApi
import com.orka.myfinances.data.api.debt.models.request.SetNotifiedApiRequest
import com.orka.myfinances.data.api.debt.models.request.SetPaidApiRequest
import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.models.toChunk
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.viewmodel.Chunk
import kotlinx.coroutines.flow.MutableSharedFlow

class DebtRepository(
    private val officeId: Id,
    private val api: DebtApi,
    private val flow: MutableSharedFlow<DebtEvent>
) : GetChunk<DebtDto>, GetDebtsChunk, GetById<DebtDto>, Insert<AddDebtRequest>, SetPaid, SetNotified {

    override suspend fun getChunk(
        size: Int,
        page: Int,
        query: String?
    ): Chunk<DebtDto>? {
        return getDebtsChunk(size, page, false, query)
    }

    override suspend fun getDebtsChunk(
        size: Int,
        page: Int,
        isCompleted: Boolean,
        query: String?
    ): Chunk<DebtDto>? {
        return api.getChunk(
            officeId = officeId.value,
            page = page,
            pageSize = size,
            search = query,
            isCompleted = isCompleted
        )?.toChunk { it.toDto() }
    }

    override suspend fun getById(id: Id): DebtDto? {
        return api.getById(id.value)?.toDto()
    }

    override suspend fun insert(request: AddDebtRequest): Boolean {
        val success = api.insert(request.toApiRequest(officeId))
        if (success) {
            flow.emit(DebtEvent)
        }
        return success
    }

    override suspend fun setPaid(id: Id): Boolean {
        val success = api.patch(id.value, SetPaidApiRequest(true))
        if (success) {
            flow.emit(DebtEvent)
        }
        return success
    }

    override suspend fun setNotified(id: Id, notified: Boolean): Boolean {
        val success = api.patch(id.value, SetNotifiedApiRequest(notified))
        if (success) {
            flow.emit(DebtEvent)
        }
        return success
    }
}

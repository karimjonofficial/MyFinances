package com.orka.myfinances.data.repositories.client

import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.models.toChunk
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.viewmodel.Chunk
import kotlinx.coroutines.flow.MutableSharedFlow

class ClientRepository(
    private val companyId: Id,
    private val api: ClientApi,
    private val flow: MutableSharedFlow<ClientEvent>
) : GetChunk<ClientDto>, GetById<ClientDto>, Insert<AddClientRequest> {

    override suspend fun getChunk(
        size: Int,
        page: Int,
        query: String?
    ): Chunk<ClientDto>? {
        return api.getChunk(
            companyId = companyId.value,
            page = page,
            pageSize = size,
            search = query,
            ordering = "first_name"
        )?.toChunk { it.toDto() }
    }

    override suspend fun getById(id: Id): ClientDto? {
        return api.getById(id.value)?.toDto()
    }

    override suspend fun insert(request: AddClientRequest): Boolean {
        val success = api.insert(request.toApiRequest(companyId))
        if (success) {
            flow.emit(ClientEvent)
        }
        return success
    }
}

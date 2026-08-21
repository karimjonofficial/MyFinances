package com.orka.myfinances.data.repositories.defaults

import com.orka.myfinances.data.models.Id

interface SetDefaultClient {
    suspend fun setDefaultClientId(id: Id)
}

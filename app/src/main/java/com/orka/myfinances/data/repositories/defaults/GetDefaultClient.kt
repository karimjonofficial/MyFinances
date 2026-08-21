package com.orka.myfinances.data.repositories.defaults

import com.orka.myfinances.data.models.Id

interface GetDefaultClient {
    suspend fun getDefaultClientId(): Id?
}

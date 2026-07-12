package com.orka.myfinances.data.repositories.info

import com.orka.myfinances.data.models.Id

interface InfoRepository {
    suspend fun getCompanyId(access: String): Id
}
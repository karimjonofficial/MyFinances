package com.orka.myfinances.application.repositories

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.info.InfoRepository

class InfoRepository(private val api: InfoApi) : InfoRepository {
    override suspend fun getCompanyId(access: String): Id {
        return api.getCompanyId(access)
    }
}
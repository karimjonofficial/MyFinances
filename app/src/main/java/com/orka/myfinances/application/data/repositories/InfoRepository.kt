package com.orka.myfinances.application.data.repositories

import com.orka.myfinances.application.data.api.InfoApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.info.InfoRepository

class InfoRepository(private val api: InfoApi) : InfoRepository {
    override suspend fun getCompanyId(access: String): Id {
        return api.getCompanyId(access)
    }
}
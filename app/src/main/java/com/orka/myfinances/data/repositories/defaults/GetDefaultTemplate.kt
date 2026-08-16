package com.orka.myfinances.data.repositories.defaults

import com.orka.myfinances.data.models.Id

interface GetDefaultTemplate {
    suspend fun getDefaultTemplateId(): Id?
}

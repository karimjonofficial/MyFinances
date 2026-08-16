package com.orka.myfinances.data.repositories.defaults

import com.orka.myfinances.data.models.Id

interface SetDefaultTemplate {
    suspend fun setDefaultTemplateId(id: Id)
}

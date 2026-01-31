package com.orka.myfinances.data.api

import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.zipped.OfficeModel
import com.orka.myfinances.lib.data.repositories.Get

interface OfficeApi : Get<Office> {
    suspend fun get(credential: Credential): OfficeModel?
}
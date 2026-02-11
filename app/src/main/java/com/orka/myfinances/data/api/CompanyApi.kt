package com.orka.myfinances.data.api

import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyModel

interface CompanyApi {
    suspend fun get(credential: Credential): CompanyModel?
}
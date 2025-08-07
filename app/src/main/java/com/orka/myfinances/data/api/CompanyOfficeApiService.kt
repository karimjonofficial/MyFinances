package com.orka.myfinances.data.api

import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.CompanyOfficeModel

interface CompanyOfficeApiService {
    suspend fun get(credential: Credential): CompanyOfficeModel?
}
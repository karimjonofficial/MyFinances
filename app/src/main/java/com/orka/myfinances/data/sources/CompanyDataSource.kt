package com.orka.myfinances.data.sources

import com.orka.myfinances.data.zipped.CompanyModel

interface CompanyDataSource {
    suspend fun get(): CompanyModel?
}
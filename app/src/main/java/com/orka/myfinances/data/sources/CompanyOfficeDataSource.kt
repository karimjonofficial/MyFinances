package com.orka.myfinances.data.sources

import com.orka.myfinances.data.zipped.CompanyOfficeModel

interface CompanyOfficeDataSource {
    suspend fun get(): List<CompanyOfficeModel>?
}
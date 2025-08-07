package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.CompanyOffice

interface CompanyOfficeRepository {
    suspend fun get(): List<CompanyOffice>?
}
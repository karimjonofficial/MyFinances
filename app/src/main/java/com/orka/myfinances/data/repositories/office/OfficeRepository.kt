package com.orka.myfinances.data.repositories.office

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetById

class OfficeRepository(
    private val api: OfficeApi,
    private val company: Company
) : Get<Office>, GetById<Office> {
    override suspend fun get(): List<Office>? {
        val response =  api.get(company.id.value)
        return response?.map { it.map(company) }
    }

    override suspend fun getById(id: Id): Office? {
        val response = api.getById(id.value)
        return response?.map(company)
    }
}
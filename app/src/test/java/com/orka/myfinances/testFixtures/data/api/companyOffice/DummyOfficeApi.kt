package com.orka.myfinances.testFixtures.data.api.companyOffice

import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.zipped.OfficeModel

class DummyOfficeApi : OfficeApi {
    override suspend fun get(credential: Credential): OfficeModel? {
        return null
    }

    override suspend fun get(): List<Office>? {
        return null
    }
}
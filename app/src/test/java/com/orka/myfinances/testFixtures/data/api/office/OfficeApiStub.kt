package com.orka.myfinances.testFixtures.data.api.office

import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.zipped.OfficeModel
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.testFixtures.resources.models.office

class OfficeApiStub : OfficeApi {
    override suspend fun get(credential: Credential): OfficeModel {
        return office.toModel()
    }

    override suspend fun get(): List<Office> {
        return listOf(office)
    }
}

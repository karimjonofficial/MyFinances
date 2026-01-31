package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.OfficeApi
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.zipped.OfficeModel
import com.orka.myfinances.fixtures.resources.models.office1
import com.orka.myfinances.fixtures.resources.models.offices
import com.orka.myfinances.lib.extensions.models.toModel

class OfficeApiImpl : OfficeApi {
    override suspend fun get(credential: Credential): OfficeModel {
        return office1.toModel()
    }

    override suspend fun get(): List<Office> {
        return offices
    }
}

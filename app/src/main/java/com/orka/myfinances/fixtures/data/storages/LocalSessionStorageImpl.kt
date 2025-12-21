package com.orka.myfinances.fixtures.data.storages

import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.data.zipped.SessionModel
import com.orka.myfinances.fixtures.resources.models.company1
import com.orka.myfinances.fixtures.resources.models.office1
import com.orka.myfinances.fixtures.resources.models.credential
import com.orka.myfinances.fixtures.resources.models.user1
import com.orka.myfinances.lib.extensions.models.makeSession
import com.orka.myfinances.lib.extensions.models.toModel

class LocalSessionStorageImpl : LocalSessionStorage {
    override suspend fun get(): SessionModel {
        return makeSession(
            credential,
            userModel = user1.toModel(),
            companyOfficeModel = office1.toModel(),
            companyModel = company1.toModel()
        ).toModel()
    }

    override suspend fun store(session: SessionModel) {}
}
package com.orka.myfinances.application.storages.credentials

import com.orka.myfinances.data.database.daos.CredentialsDao
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.storages.credentials.CredentialsStorage

class CredentialsStorageImpl(private val dao: CredentialsDao) : CredentialsStorage {
    override suspend fun get(): Credentials? {
        return dao.get()?.toCredentials()
    }

    override suspend fun set(credentials: Credentials) {
        if (dao.isEmpty())
            dao.insert(credentials.access, credentials.refresh)
        else dao.update(credentials.access, credentials.refresh)
    }

    override suspend fun clear() {
        dao.clear()
    }
}
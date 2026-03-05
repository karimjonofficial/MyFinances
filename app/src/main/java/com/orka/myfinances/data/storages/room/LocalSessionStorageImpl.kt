package com.orka.myfinances.data.storages.room

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.data.zipped.SessionModel
import com.orka.myfinances.fixtures.data.api.CredentialsModel

class LocalSessionStorageImpl(private val sessionDao: SessionDao) : LocalSessionStorage {
    override suspend fun get(): SessionModel? {
        val entity = sessionDao.getSession() ?: return null
        return SessionModel(
            credentials = Credentials(
                access = entity.access,
                refresh = entity.refresh
            ),
            officeId = entity.officeId
        )
    }

    override suspend fun store(session: SessionModel) {
        sessionDao.clearSession()
        sessionDao.insertSession(
            SessionEntity(
                access = session.credentials.access,
                refresh = session.credentials.refresh,
                officeId = session.officeId
            )
        )
    }

    override suspend fun updateCredentials(credentials: CredentialsModel) {
        sessionDao.updateCredentials(credentials.access, credentials.refresh)
    }

    override suspend fun clear() {
        sessionDao.clearSession()
    }
}

package com.orka.myfinances.data.storages.room

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.storages.SessionStorage
import com.orka.myfinances.data.zipped.SessionModel

class LocalSessionStorage(private val sessionDao: SessionDao) : SessionStorage {
    override suspend fun get(): SessionModel? {
        val entity = sessionDao.getSession() ?: return null
        return SessionModel(
            credentials = Credentials(
                access = entity.access,
                refresh = entity.refresh
            ),
            branchId = entity.branchId
        )
    }

    override suspend fun store(session: SessionModel) {
        sessionDao.clearSession()
        sessionDao.insertSession(
            SessionEntity(
                access = session.credentials.access,
                refresh = session.credentials.refresh,
                branchId = session.branchId
            )
        )
    }

    override suspend fun updateCredentials(credentials: Credentials) {
        sessionDao.updateCredentials(credentials.access, credentials.refresh)
    }

    override suspend fun clear() {
        sessionDao.clearSession()
    }
}

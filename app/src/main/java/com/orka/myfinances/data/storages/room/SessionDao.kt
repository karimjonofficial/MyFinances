package com.orka.myfinances.data.storages.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SessionDao {
    @Query("SELECT * FROM session LIMIT 1")
    suspend fun getSession(): SessionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity)

    @Query("DELETE FROM session")
    suspend fun clearSession()

    @Query("UPDATE session SET access = :access, refresh = :refresh")
    suspend fun updateCredentials(access: String, refresh: String)
}

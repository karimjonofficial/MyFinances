package com.orka.myfinances.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.orka.myfinances.data.database.entities.CredentialsEntity

@Dao
interface CredentialsDao {
    @Query("SELECT * FROM credentials LIMIT 1")
    suspend fun get(): CredentialsEntity?

    @Query("DELETE FROM credentials")
    suspend fun clear()

    @Query("UPDATE credentials SET access = :access, refresh = :refresh")
    suspend fun update(access: String, refresh: String)

    @Query("INSERT INTO credentials (access, refresh) VALUES (:access, :refresh)")
    suspend fun insert(access: String, refresh: String)

    @Query("SELECT COUNT(*) FROM credentials")
    suspend fun isEmpty(): Boolean
}
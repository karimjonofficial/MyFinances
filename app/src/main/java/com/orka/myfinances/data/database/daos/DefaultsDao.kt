package com.orka.myfinances.data.database.daos

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DefaultsDao {
    @Query("SELECT value FROM defaults where name = 'catalog'")
    suspend fun getDefaultCatalogId(): Int?

    @Query("SELECT value FROM defaults where name = 'branch'")
    suspend fun getDefaultBranchId(): Int?

    @Query("UPDATE defaults SET value = :id WHERE name = 'catalog'")
    suspend fun setDefaultCatalogId(id: Int?): Int

    @Query("UPDATE defaults SET value = :id WHERE name = 'branch'")
    suspend fun setDefaultBranchId(id: Int?): Int

    @Query("DELETE FROM defaults")
    suspend fun clear()
}
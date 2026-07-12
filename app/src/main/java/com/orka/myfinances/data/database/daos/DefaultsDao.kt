package com.orka.myfinances.data.database.daos

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DefaultsDao {
    @Query("SELECT value FROM defaults where name = 'catalog'")
    suspend fun getDefaultCatalogId(): Int?

    @Query("INSERT INTO defaults (name, value) VALUES ('catalog', :id)")
    suspend fun insertDefaultCatalogId(id: Int)

    @Query("UPDATE defaults SET value = :id WHERE name = 'catalog'")
    suspend fun setDefaultCatalogId(id: Int?)


    @Query("SELECT value FROM defaults where name = 'branch'")
    suspend fun getDefaultBranchId(): Int?

    @Query("INSERT INTO defaults (name, value) VALUES ('branch', :id)")
    suspend fun insertDefaultBranchId(id: Int)

    @Query("UPDATE defaults SET value = :id WHERE name = 'branch'")
    suspend fun setDefaultBranchId(id: Int?)


    @Query("DELETE FROM defaults")
    suspend fun clear()
}
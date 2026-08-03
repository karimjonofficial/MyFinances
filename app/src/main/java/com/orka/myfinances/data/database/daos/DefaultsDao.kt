package com.orka.myfinances.data.database.daos

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DefaultsDao {
    //Catalog
    @Query("SELECT value FROM defaults where name = 'catalog'")
    suspend fun getDefaultCatalogId(): Int?

    @Query("INSERT INTO defaults (name, value) VALUES ('catalog', :id)")
    suspend fun insertDefaultCatalogId(id: Int)

    @Query("UPDATE defaults SET value = :id WHERE name = 'catalog'")
    suspend fun setDefaultCatalogId(id: Int?)

    //Branch
    @Query("SELECT value FROM defaults where name = 'branch'")
    suspend fun getDefaultBranchId(): Int?

    @Query("INSERT INTO defaults (name, value) VALUES ('branch', :id)")
    suspend fun insertDefaultBranchId(id: Int)

    @Query("UPDATE defaults SET value = :id WHERE name = 'branch'")
    suspend fun setDefaultBranchId(id: Int?)


    //Printer
    @Query("SELECT value FROM defaults where name = 'printer'")
    suspend fun getDefaultPrinterId(): Int?

    @Query("INSERT INTO defaults (name, value) VALUES ('printer', :id)")
    suspend fun insertDefaultPrinterId(id: Int)

    @Query("UPDATE defaults SET value = :id WHERE name = 'printer'")
    suspend fun setDefaultPrinterId(id: Int?)


    //Main
    @Query("DELETE FROM defaults")
    suspend fun clear()
}
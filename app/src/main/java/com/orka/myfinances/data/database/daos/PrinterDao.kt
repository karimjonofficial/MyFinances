package com.orka.myfinances.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.orka.myfinances.data.database.entities.PrinterEntity

@Dao
interface PrinterDao {
    @Query("SELECT * FROM printers")
    suspend fun getAllPrinters(): List<PrinterEntity>

    @Query("SELECT * FROM printers WHERE id = :id")
    suspend fun getPrinterById(id: Int): PrinterEntity?

    @Query("INSERT INTO printers (name, address) VALUES (:name, :address)")
    suspend fun addPrinter(name: String, address: String)
}
package com.orka.myfinances.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "printers")
data class PrinterEntity(
    @PrimaryKey(true) val id: Int,
    val name: String,
    val address: String
)
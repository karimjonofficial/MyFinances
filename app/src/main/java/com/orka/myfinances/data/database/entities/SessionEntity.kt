package com.orka.myfinances.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey val id: Int = 0,
    val access: String,
    val refresh: String,
    val branchId: Int,
)
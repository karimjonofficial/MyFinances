package com.orka.myfinances.data.storages.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class SessionEntity(
    @PrimaryKey val id: Int = 0,
    val access: String,
    val refresh: String,
    val officeId: Int,
)

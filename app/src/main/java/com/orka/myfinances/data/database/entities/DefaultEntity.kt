package com.orka.myfinances.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("defaults")
data class DefaultEntity(
    @PrimaryKey val name: String,
    val value: Int?
)

package com.orka.myfinances.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials")
data class CredentialsEntity(
    @PrimaryKey val id: Int = 0,
    val access: String,
    val refresh: String
)

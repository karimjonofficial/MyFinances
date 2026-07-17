package com.orka.myfinances.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pinned_categories")
data class PinnedCategoryEntity(
    @PrimaryKey val id: Int,
    val index: Int
)

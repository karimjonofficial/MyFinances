package com.orka.myfinances.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orka.myfinances.data.database.daos.CredentialsDao
import com.orka.myfinances.data.database.daos.DefaultsDao
import com.orka.myfinances.data.database.daos.PinnedCategoriesDao
import com.orka.myfinances.data.database.daos.PrinterDao
import com.orka.myfinances.data.database.entities.CredentialsEntity
import com.orka.myfinances.data.database.entities.DefaultEntity
import com.orka.myfinances.data.database.entities.PinnedCategoryEntity
import com.orka.myfinances.data.database.entities.PrinterEntity

@Database(
    entities = [
        CredentialsEntity::class,
        DefaultEntity::class,
        PinnedCategoryEntity::class,
        PrinterEntity::class
    ],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun defaultsDao(): DefaultsDao
    abstract fun credentialsDao(): CredentialsDao
    abstract fun pinnedCategoriesDao(): PinnedCategoriesDao
    abstract fun printersDao(): PrinterDao
}
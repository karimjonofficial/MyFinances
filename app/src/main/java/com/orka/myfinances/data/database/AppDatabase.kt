package com.orka.myfinances.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.orka.myfinances.data.database.daos.CredentialsDao
import com.orka.myfinances.data.database.daos.DefaultsDao
import com.orka.myfinances.data.database.entities.CredentialsEntity
import com.orka.myfinances.data.database.entities.DefaultEntity

@Database(
    entities = [
        CredentialsEntity::class,
        DefaultEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun defaultsDao(): DefaultsDao
    abstract fun credentialsDao(): CredentialsDao
}
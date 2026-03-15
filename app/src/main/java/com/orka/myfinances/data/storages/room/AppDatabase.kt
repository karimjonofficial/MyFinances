package com.orka.myfinances.data.storages.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SessionEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
}
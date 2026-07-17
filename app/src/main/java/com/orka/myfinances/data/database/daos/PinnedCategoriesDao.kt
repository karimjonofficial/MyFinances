package com.orka.myfinances.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.orka.myfinances.data.database.entities.PinnedCategoryEntity

@Dao
interface PinnedCategoriesDao {
    @Query("SELECT * FROM pinned_categories")
    suspend fun getAll(): List<PinnedCategoryEntity>

    @Query("SELECT `index` FROM pinned_categories ORDER BY `index` DESC LIMIT 1")
    suspend fun getLastIndex(): Int?

    @Query("INSERT INTO pinned_categories (id, `index`) VALUES (:id, :index)")
    suspend fun insert(id: Int, index: Int)
}
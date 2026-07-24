package com.pmdm.mygamestore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pmdm.mygamestore.data.local.entities.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history  WHERE username = :username ORDER BY timestamp DESC LIMIT 5")
    fun getSearchHistory(username: String): Flow<SearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE username = :usename")
    suspend fun deleteSearchHistory(usename: String)
}
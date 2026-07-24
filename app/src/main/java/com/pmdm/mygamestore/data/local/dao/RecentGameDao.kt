package com.pmdm.mygamestore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pmdm.mygamestore.data.local.entities.RecentGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentGameDao {
    @Query("SELECT * FROM recent_game WHERE username = :username ORDER BY timeStamp DESC LIMIT 5")
    fun getRecentGames(username: String): Flow<RecentGameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentGame(recentGame: RecentGameEntity)

    @Query("DELETE FROM recent_game WHERE username = :username")
    suspend fun deleteRecentGame(username: String)
}
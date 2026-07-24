package com.pmdm.mygamestore.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pmdm.mygamestore.data.local.entities.GameNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameNoteDao {

    @Query("SELECT * FROM game_note WHERE username = :username AND gameId = :gameId")
    fun getNote(gameId: Int, username: String): Flow<GameNoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteOrUpdateNote(note: GameNoteEntity)

    @Query("DELETE FROM game_note WHERE username = :username AND gameId = :gameId")
    suspend fun deleteNote(gameId: Int, username: String)
}
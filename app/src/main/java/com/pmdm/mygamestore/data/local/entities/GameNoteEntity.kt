package com.pmdm.mygamestore.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.pmdm.mygamestore.domain.model.ProgressStatus


@Entity(
    tableName = "game_note",
    primaryKeys = ["gameId", "username"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["username"])]
)
data class GameNoteEntity(
    val gameId: Int,
    val username: String,
    val note: String,
    val progressStatus: ProgressStatus, // PENDING, PLAYING, COMPLETED, ABANDONED...
    val lastUpdated: Long
)

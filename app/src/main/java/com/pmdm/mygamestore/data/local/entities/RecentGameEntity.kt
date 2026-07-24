package com.pmdm.mygamestore.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recent_game",
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
data class RecentGameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val gameId: Int,
    val timeStamp: Long
)

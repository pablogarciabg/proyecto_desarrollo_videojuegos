package com.pmdm.mygamestore.data.local.entities

import android.R
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "search_history",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["username"],
            childColumns = ["username"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val query: String,
    val timeStamp: Long
)

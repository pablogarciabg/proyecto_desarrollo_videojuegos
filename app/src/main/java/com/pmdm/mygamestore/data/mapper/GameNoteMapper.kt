package com.pmdm.mygamestore.data.mapper

import com.pmdm.mygamestore.data.local.entities.GameNoteEntity
import com.pmdm.mygamestore.domain.model.GameNote

fun GameNoteEntity.toDomain(): GameNote {
    return GameNote(
        gameId = gameId,
        note = note,
        progressStatus = progressStatus,
        lastUpdated = lastUpdated
    )
}
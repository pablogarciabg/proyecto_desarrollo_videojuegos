package com.pmdm.mygamestore.domain.model

data class GameNote(
    val gameId: Int,
    val note: String,
    val progressStatus: ProgressStatus,
    val lastUpdated: Long
)

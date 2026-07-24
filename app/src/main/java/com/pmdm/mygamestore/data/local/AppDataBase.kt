package com.pmdm.mygamestore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pmdm.mygamestore.data.local.dao.GameNoteDao
import com.pmdm.mygamestore.data.local.dao.LibraryDao
import com.pmdm.mygamestore.data.local.dao.RecentGameDao
import com.pmdm.mygamestore.data.local.dao.SearchHistoryDao
import com.pmdm.mygamestore.data.local.dao.UserDao
import com.pmdm.mygamestore.data.local.entities.GameNoteEntity
import com.pmdm.mygamestore.data.local.entities.LibraryEntity
import com.pmdm.mygamestore.data.local.entities.RecentGameEntity
import com.pmdm.mygamestore.data.local.entities.SearchHistoryEntity
import com.pmdm.mygamestore.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        LibraryEntity::class,
        SearchHistoryEntity::class,
        RecentGameEntity::class,
        GameNoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun libraryDao(): LibraryDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun recentGameDao(): RecentGameDao
    abstract fun gameNoteDao(): GameNoteDao
}
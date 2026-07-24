package com.pmdm.mygamestore.data.local

import android.provider.ContactsContract
import androidx.room.TypeConverter
import com.pmdm.mygamestore.domain.model.LibraryStatus
import com.pmdm.mygamestore.domain.model.ProgressStatus

class RoomConverters {
    @TypeConverter
    fun fromLibraryStatus(status: LibraryStatus): String = status.name

    @TypeConverter
    fun toLibraryStatus(value: String): LibraryStatus = LibraryStatus.valueOf(value)

    @TypeConverter
    fun fromProgressStatus(status: ProgressStatus): String = status.toString()

    @TypeConverter
    fun toProgressStatus(value: String): ProgressStatus = ProgressStatus.valueOf(value)
}
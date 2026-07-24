package com.pmdm.mygamestore

import android.app.Application
import androidx.room.Room
import com.pmdm.mygamestore.data.local.AppDataBase

class MyGameStoreApp : Application() {

    companion object {
        lateinit var database: AppDataBase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        //Iniciaizacion de la base de datos como Singleton
        database = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "mygamestore_db"
        ).build()
    }
}

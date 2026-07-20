package com.pmdm.mygamestore.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * 🔧 Extensión de Context para acceder a DataStore de preferencias
 *
 * La función `by preferencesDataStore` crea una instancia singleton de DataStore
 * asociada al contexto de la aplicación.
 *
 * @param name Nombre del archivo de preferencias ("user_session.preferences_pb")
 */
val Context.dataStorePreferences: DataStore<Preferences> by preferencesDataStore(name = "user_session")
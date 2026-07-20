package com.pmdm.mygamestore.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 🔧 Implementación de SessionManager usando DataStore
 *
 * @param context Contexto de la aplicación para acceder a DataStore
 */
class SessionManagerImpl(
    private val context: Context
) : SessionManager {
    //Acceso al datastore a traves de la extnsion
    private val datastore = context.dataStorePreferences

    companion object {
        /**
         * Keys tipadas para acceder a las preferencias
         * Usar keys garantiza type-safety y evita errores de tipeo
         */
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USERNAME = stringPreferencesKey("username")
    }

    /**
     * Guarda la sesion del usuario en el Data Store
     */
    override suspend fun saveSession(username: String) {
        datastore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[USERNAME] = username
        }
    }

    /**
     * Verifica si el usuario tiene sesion activa
     * @return Flow que emite true si está logueado, false en caso contrario
     */
    override fun isUserLoggedIn(): Flow<Boolean> {
        return datastore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }
    }

    /**
     * Obtiene el nombre del usuario de la sesión activa
     *
     * @return Flow que emite el username o null si no hay sesión
     */
    override fun getUserName(): Flow<String?> {
        return datastore.data.map { preferences ->
            preferences[USERNAME]
        }
    }

    /**
     * Limpia toda la sesion del usuario
     */
    override suspend fun clearSession() {
        datastore.edit { preferences ->
            preferences.clear()
        }
    }
}
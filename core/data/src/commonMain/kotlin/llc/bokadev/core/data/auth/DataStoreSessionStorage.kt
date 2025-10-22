package llc.bokadev.core.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import llc.bokadev.core.data.dto.AuthInfoSerializable
import llc.bokadev.core.data.mappers.toDomain
import llc.bokadev.core.data.mappers.toSerializable
import llc.bokadev.core.domain.auth.AuthInfo
import llc.bokadev.core.domain.auth.SessionStorage

class DataStoreSessionStorage(
    private val dataStore: DataStore<Preferences>
) : SessionStorage {

    private val authInfoKey = stringPreferencesKey("KEY_AUTH_INFO")

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override fun observeAuthInfo(): Flow<AuthInfo?> {
        return dataStore.data.map { preferences ->
            val serializedJson = preferences[authInfoKey]
            serializedJson?.let {
                json.decodeFromString<AuthInfoSerializable>(it).toDomain()
            }
        }
    }

    override suspend fun set(info: AuthInfo?) {
        if (info == null) {
            dataStore.edit {
                it.remove(authInfoKey)
            }
            return
        }

        val serialized = json.encodeToString(info.toSerializable())
        dataStore.edit { prefs ->
            prefs[authInfoKey] = serialized
        }

    }
}
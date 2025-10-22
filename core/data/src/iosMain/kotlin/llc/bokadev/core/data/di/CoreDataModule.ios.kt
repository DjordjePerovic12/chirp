package llc.bokadev.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import llc.bokadev.core.data.auth.createDataStore
import org.koin.dsl.module


actual val platformCoreDataModule = module {
    single<HttpClientEngine> { Darwin.create() }
    single<DataStore<Preferences>> { createDataStore() }
}
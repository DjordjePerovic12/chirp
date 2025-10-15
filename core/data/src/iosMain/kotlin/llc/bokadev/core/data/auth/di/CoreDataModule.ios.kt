package llc.bokadev.core.data.auth.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module


actual val platformCoreDataModule = module {
    single<HttpClientEngine> { Darwin.create() }
}
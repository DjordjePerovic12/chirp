package llc.bokadev.core.data.di

import llc.bokadev.core.data.auth.DataStoreSessionStorage
import llc.bokadev.core.data.auth.KtorAuthService
import llc.bokadev.core.data.logging.KermitLogger
import llc.bokadev.core.data.networking.HttpClientFactory
import llc.bokadev.core.domain.auth.AuthService
import llc.bokadev.core.domain.auth.SessionStorage
import llc.bokadev.core.domain.logging.ChirpLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)
    single<ChirpLogger> { KermitLogger }
    single {
        HttpClientFactory(get()).create(get())
    }
    singleOf(::KtorAuthService) bind AuthService::class
    singleOf(::DataStoreSessionStorage) bind SessionStorage::class
}
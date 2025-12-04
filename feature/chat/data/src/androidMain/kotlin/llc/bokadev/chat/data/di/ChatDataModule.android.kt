package llc.bokadev.chat.data.di

import llc.bokadev.chat.data.lifecycle.AppLifecycleObserver
import llc.bokadev.chat.data.network.ConnectionErrorHandler
import llc.bokadev.chat.data.network.ConnectivityObserver
import llc.bokadev.chat.database.DatabaseFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory(androidContext()) }
    singleOf(::AppLifecycleObserver)
    singleOf(::ConnectivityObserver)
    singleOf(::ConnectionErrorHandler)
}
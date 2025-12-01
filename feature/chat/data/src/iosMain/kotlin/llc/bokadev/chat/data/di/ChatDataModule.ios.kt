package llc.bokadev.chat.data.di

import androidx.room.Database
import llc.bokadev.chat.data.lifecycle.AppLifecycleObserver
import llc.bokadev.chat.database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory() }
    singleOf(::AppLifecycleObserver)
}
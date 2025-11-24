package llc.bokadev.chat.data.di

import llc.bokadev.chat.database.DatabaseFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory(androidContext()) }
}
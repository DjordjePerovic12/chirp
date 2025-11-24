package llc.bokadev.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import llc.bokadev.chat.data.chat.KtorChatParticipantService
import llc.bokadev.chat.data.chat.KtorChatService
import llc.bokadev.chat.database.DatabaseFactory
import llc.bokadev.chat.domain.chat.ChatParticipantService
import llc.bokadev.chat.domain.chat.ChatService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformChatDataModule: Module
val chatDataModule = module {
    includes(platformChatDataModule)

    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}
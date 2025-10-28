package llc.bokadev.chat.data.di

import llc.bokadev.chat.data.chat.KtorChatParticipantService
import llc.bokadev.chat.data.chat.KtorChatService
import llc.bokadev.chat.domain.chat.ChatParticipantService
import llc.bokadev.chat.domain.chat.ChatService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDataModule = module {
    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
}
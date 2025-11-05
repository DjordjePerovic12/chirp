package llc.bokadev.chat.presentation.di

import llc.bokadev.chat.presentation.chat_list.ChatListScreenViewModel
import llc.bokadev.chat.presentation.chat_list_detail.ChatListDetailViewModel
import llc.bokadev.chat.presentation.create_chat.CreateChatViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatPresentationModule = module {
    viewModelOf(::ChatListScreenViewModel)
    viewModelOf(::ChatListDetailViewModel)
    viewModelOf(::CreateChatViewModel)
}
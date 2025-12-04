package llc.bokadev.chat.domain.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import llc.bokadev.chat.domain.models.ChatMessage
import llc.bokadev.chat.domain.models.ConnectionState
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult

interface ChatConnectionClient {

    val chatMessages: Flow<ChatMessage>
    val connectionState: StateFlow<ConnectionState>
}
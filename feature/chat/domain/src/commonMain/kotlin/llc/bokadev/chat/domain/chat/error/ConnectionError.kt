package llc.bokadev.chat.domain.chat.error

import llc.bokadev.core.domain.util.Error

enum class ConnectionError: Error {
    NOT_CONNECTED,
    MESSAGE_SEND_FAILED
}
package llc.bokadev.chirp

sealed interface MainEvent {
    data object OnSessionExpired: MainEvent
}
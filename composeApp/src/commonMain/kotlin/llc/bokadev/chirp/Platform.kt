package llc.bokadev.chirp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
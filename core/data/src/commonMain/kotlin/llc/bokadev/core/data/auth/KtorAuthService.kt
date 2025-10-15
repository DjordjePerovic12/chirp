package llc.bokadev.core.data.auth

import io.ktor.client.HttpClient
import llc.bokadev.core.data.dto.requests.RegisterRequest
import llc.bokadev.core.data.networking.post
import llc.bokadev.core.domain.auth.AuthService
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult

class KtorAuthService(
    private val httpClient: HttpClient
) : AuthService {
    override suspend fun register(
        email: String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/register",
            body = RegisterRequest(
                email = email,
                username = username,
                password = password
            )
        )
    }
}
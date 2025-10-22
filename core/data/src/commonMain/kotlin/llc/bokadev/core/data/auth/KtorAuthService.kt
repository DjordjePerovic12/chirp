package llc.bokadev.core.data.auth

import io.ktor.client.HttpClient
import llc.bokadev.core.data.dto.AuthInfoSerializable
import llc.bokadev.core.data.dto.requests.EmailRequest
import llc.bokadev.core.data.dto.requests.LoginRequest
import llc.bokadev.core.data.dto.requests.RegisterRequest
import llc.bokadev.core.data.mappers.toDomain
import llc.bokadev.core.data.networking.get
import llc.bokadev.core.data.networking.post
import llc.bokadev.core.domain.auth.AuthInfo
import llc.bokadev.core.domain.auth.AuthService
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult
import llc.bokadev.core.domain.util.Result
import llc.bokadev.core.domain.util.map

class KtorAuthService(
    private val httpClient: HttpClient
) : AuthService {

    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthInfo, DataError.Remote> {
        return httpClient.post<LoginRequest, AuthInfoSerializable>(
            route = "/auth/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        ).map { authInfoSerializable ->
            authInfoSerializable.toDomain()
        }
    }

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

    override suspend fun resendVerificationEmail(
        email: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/resend-verification",
            body = EmailRequest(
                email = email
            )
        )
    }

    override suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote> {
        return httpClient.get(
            route = "/auth/verify",
            queryParams = mapOf("token" to token)
        )
    }
}
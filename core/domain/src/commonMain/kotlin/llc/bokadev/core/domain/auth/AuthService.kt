package llc.bokadev.core.domain.auth

import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult

interface AuthService {
    suspend fun register(
        email: String,
        username: String,
        password: String
    ): EmptyResult<DataError.Remote>

    suspend fun resendVerificationEmail(
        email: String
    ): EmptyResult<DataError.Remote>

    suspend fun verifyEmail(
        token: String
    ): EmptyResult<DataError.Remote>
}
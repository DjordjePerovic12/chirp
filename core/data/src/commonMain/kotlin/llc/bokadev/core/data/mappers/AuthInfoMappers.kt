package llc.bokadev.core.data.mappers

import llc.bokadev.core.data.dto.AuthInfoSerializable
import llc.bokadev.core.data.dto.UserSerializable
import llc.bokadev.core.domain.auth.AuthInfo
import llc.bokadev.core.domain.auth.User

fun AuthInfoSerializable.toDomain() : AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toDomain()
    )
}

fun UserSerializable.toDomain() : User {
    return User(
        id = id,
        email = email,
        username = username,
        hasVerifiedEmail = hasVerifiedEmail,
        profilePictureUrl = profilePictureUrl
    )
}
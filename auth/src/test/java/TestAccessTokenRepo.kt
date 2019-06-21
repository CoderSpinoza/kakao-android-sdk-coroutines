package com.kakao.sdk.auth

import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.auth.model.AccessTokenResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import java.util.Date

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
class TestAccessTokenRepo(var token: AccessToken) : AccessTokenRepo {
    @ExperimentalCoroutinesApi
    override fun observe(): ConflatedBroadcastChannel<AccessToken> = ConflatedBroadcastChannel(token)

    override fun fromCache(): AccessToken {
        return token
    }

    override fun toCache(accessTokenResponse: AccessTokenResponse): AccessToken {
        token = AccessToken(
                accessTokenResponse.accessToken,
                Date(Date().time + 1000L * accessTokenResponse.accessTokenExpiresIn),
                accessTokenResponse.refreshToken,
                Date(Date().time + 1000L * (accessTokenResponse.refreshTokenExpiresIn ?: 0)),
                listOf()
        )
        return token
    }

    override fun clearCache() {
        token = AccessToken()
    }
}
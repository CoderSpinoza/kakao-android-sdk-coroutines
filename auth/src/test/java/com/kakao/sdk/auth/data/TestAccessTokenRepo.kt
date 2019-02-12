package com.kakao.sdk.auth.data

import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.auth.model.AccessTokenResponse
import io.reactivex.Observable
import java.util.*

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
class TestAccessTokenRepo(var token: AccessToken): AccessTokenRepo {
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

    override fun observe(): Observable<AccessToken> {
        return Observable.just(token)
    }
}
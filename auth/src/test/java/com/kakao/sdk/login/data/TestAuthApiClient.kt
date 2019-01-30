package com.kakao.sdk.login.data

import com.kakao.sdk.login.domain.AuthApiClient
import com.kakao.sdk.login.entity.AccessTokenResponse
import io.reactivex.Single
import java.util.*

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
class TestAuthApiClient : AuthApiClient {
    override fun issueAccessToken(clientId: String, redirectUri: String, approvalType: String, androidKeyHash: String, clientSecret: String?, authCode: String): Single<AccessTokenResponse> {
        return success()
    }

    override fun refreshAccessToken(clientId: String, redirectUri: String, approvalType: String, androidKeyHash: String, clientSecret: String?, refreshToken: String): Single<AccessTokenResponse> {
        return success()
    }


    private fun success(): Single<AccessTokenResponse> {
        return Single.just(
                AccessTokenResponse(
                        accessToken = "test_access_token",
                        accessTokenExpiresIn = Date().time,
                        refreshToken = "test_refresh_token",
                        refreshTokenExpiresIn = Date().time,
                        tokenType = "bearer",
                        scopes = ""
                )
        )
    }
}
package com.kakao.sdk.auth.data

import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.AccessTokenResponse
import io.reactivex.Single
import java.util.*

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
open class TestAuthApiClient : AuthApiClient {
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
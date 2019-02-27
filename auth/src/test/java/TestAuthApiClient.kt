package com.kakao.sdk.auth

import com.kakao.sdk.auth.model.AccessTokenResponse
import java.util.Date

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
open class TestAuthApiClient : AuthApiClient {
    override suspend fun issueAccessToken(
        authCode: String,
        clientId: String,
        redirectUri: String,
        approvalType: String,
        androidKeyHash: String,
        clientSecret: String?
    ): AccessTokenResponse {
        return success()
    }

    override suspend fun refreshAccessToken(
        refreshToken: String,
        clientId: String,
        approvalType: String,
        androidKeyHash: String,
        clientSecret: String?
    ): AccessTokenResponse {
        return success()
    }

    private fun success(): AccessTokenResponse {
        return AccessTokenResponse(
                accessToken = "test_access_token",
                accessTokenExpiresIn = Date().time,
                refreshToken = "test_refresh_token",
                refreshTokenExpiresIn = Date().time,
                tokenType = "bearer",
                scopes = ""
        )
    }
}
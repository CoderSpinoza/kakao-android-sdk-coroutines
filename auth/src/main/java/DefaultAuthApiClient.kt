package com.kakao.sdk.auth

import com.kakao.sdk.auth.model.AccessTokenResponse
import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.auth.model.AuthErrorResponse
import com.kakao.sdk.auth.exception.AuthResponseException
import com.kakao.sdk.common.KakaoGsonFactory
import retrofit2.HttpException

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 28..
 */
class DefaultAuthApiClient(
        val authApi: AuthApi = OAuthApiFactory.kauth.create(AuthApi::class.java),
        val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance
): AuthApiClient {

    override suspend fun issueAccessToken(authCode: String,
                                  clientId: String,
                                  redirectUri: String,
                                  approvalType: String,
                                  androidKeyHash: String,
                                  clientSecret: String?
    ): AccessTokenResponse {
        val response = transformError {
            authApi.issueAccessToken(
                    clientId = clientId,
                    redirectUri = redirectUri,
                    androidKeyHash = androidKeyHash,
                    authCode = authCode,
                    approvalType = approvalType,
                    clientSecret = clientSecret
            ).await()
        }
        accessTokenRepo.toCache(response)
        return response
    }

    override suspend fun refreshAccessToken(refreshToken: String,
                                    clientId: String,
                                    approvalType: String,
                                    androidKeyHash: String,
                                    clientSecret: String?
    ): AccessTokenResponse {
        val response = transformError {
            authApi.issueAccessToken(
                    clientId = clientId,
                    redirectUri = null,
                    approvalType = approvalType,
                    androidKeyHash = androidKeyHash,
                    refreshToken = refreshToken,
                    clientSecret = clientSecret,
                    grantType = Constants.REFRESH_TOKEN
            ).await()
        }
        accessTokenRepo.toCache(response)
        return response
    }

    suspend fun <T> transformError(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: HttpException) {
            val errorString = e.response().errorBody()?.string()
            val response = KakaoGsonFactory.base.fromJson(errorString, AuthErrorResponse::class.java)
            throw AuthResponseException(e.code(), response)
        }
    }
}
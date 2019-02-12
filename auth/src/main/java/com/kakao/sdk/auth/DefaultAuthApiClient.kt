package com.kakao.sdk.auth

import com.google.gson.Gson
import com.kakao.sdk.auth.model.AccessTokenResponse
import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.auth.model.AuthErrorResponse
import com.kakao.sdk.auth.exception.AuthException
import io.reactivex.Single
import io.reactivex.SingleTransformer
import retrofit2.HttpException

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 28..
 */
class DefaultAuthApiClient(
        val authApi: AuthApi = OAuthApiFactory.kauth.create(AuthApi::class.java),
        val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance
): AuthApiClient {

    override fun issueAccessToken(authCode: String,
                                  clientId: String,
                                  redirectUri: String,
                                  approvalType: String,
                                  androidKeyHash: String,
                                  clientSecret: String?
    ): Single<AccessTokenResponse> {
        return authApi.issueAccessToken(
                clientId = clientId,
                redirectUri = redirectUri,
                androidKeyHash = androidKeyHash,
                authCode = authCode,
                approvalType = approvalType,
                clientSecret = clientSecret
        ).compose(handleAuthError()).doOnSuccess { accessTokenRepo.toCache(it) }
    }

    override fun refreshAccessToken(refreshToken: String,
                                    clientId: String,
                                    approvalType: String,
                                    androidKeyHash: String,
                                    clientSecret: String?
    ): Single<AccessTokenResponse> {
        return authApi.issueAccessToken(
                clientId = clientId,
                redirectUri = null,
                approvalType = approvalType,
                androidKeyHash = androidKeyHash,
                refreshToken = refreshToken,
                clientSecret = clientSecret,
                grantType = Constants.REFRESH_TOKEN
        ).compose(handleAuthError()).doOnSuccess { accessTokenRepo.toCache(it) }
    }

    fun <T> handleAuthError(): SingleTransformer<T ,T> {
        return SingleTransformer { it.onErrorResumeNext { Single.error(translateError(it)) }
        }
    }

    fun translateError(t: Throwable): Throwable {
        if (t is HttpException) {
            val errorString = t.response().errorBody()?.string()
            val response = Gson().fromJson(errorString, AuthErrorResponse::class.java)
            return AuthException(t.code(), response)
        }
        return t
    }
}
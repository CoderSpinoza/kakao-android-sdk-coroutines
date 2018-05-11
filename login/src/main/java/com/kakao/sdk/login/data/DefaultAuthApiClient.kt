package com.kakao.sdk.login.data

import com.google.gson.Gson
import com.kakao.sdk.login.entity.token.AccessTokenResponse
import com.kakao.sdk.login.domain.AuthApi
import com.kakao.sdk.login.domain.AuthApiClient
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException

/**
 * @author kevin.kang. Created on 2018. 3. 28..
 */
class DefaultAuthApiClient(val authApi: AuthApi = ApiService.kauth.create(AuthApi::class.java)): AuthApiClient {
    val responseSubject = PublishSubject.create<AccessTokenResponse>()
    val responseUpdates = responseSubject.hide()

    override fun issueAccessToken(clientId: String,
                                  redirectUri: String,
                                  approvalType: String,
                                  androidKeyHash: String,
                                  clientSecret: String?,
                                  authCode: String
    ): Single<AccessTokenResponse> {
        return authApi.issueAccessToken(
                clientId = clientId,
                redirectUri = redirectUri,
                androidKeyHash = androidKeyHash,
                authCode = authCode,
                approvalType = approvalType,
                clientSecret = clientSecret
        ).subscribeOn(Schedulers.io())
                .compose(handleAuthError())
                .doOnSuccess { responseSubject.onNext(it) }
    }

    override fun refreshAccessToken(clientId: String,
                                    redirectUri: String,
                                    approvalType: String,
                                    androidKeyHash: String,
                                    clientSecret: String?,
                                    refreshToken: String
    ): Single<AccessTokenResponse> {
        return authApi.issueAccessToken(
                clientId = clientId,
                redirectUri = redirectUri,
                approvalType = approvalType,
                androidKeyHash = androidKeyHash,
                refreshToken = refreshToken,
                clientSecret = clientSecret,
                grantType = com.kakao.sdk.login.Constants.REFRESH_TOKEN
        ).subscribeOn(Schedulers.io())
                .compose(handleAuthError())
                .doOnSuccess { responseSubject.onNext(it) }
    }

    fun <T> handleAuthError(): SingleTransformer<T ,T> {
        return SingleTransformer { it.onErrorResumeNext { Single.error(translateError(it)) }
        }
    }

    private fun translateError(t: Throwable): Throwable {
        if (t is HttpException) {
            val errorString = t.response().errorBody()?.string()
            val response = Gson().fromJson(errorString, AuthErrorResponse::class.java)
            return AuthException(t.code(), response)
        }
        return t
    }
}
package com.kakao.sdk.auth.network

import com.google.gson.Gson
import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.exception.AgeVerificationException
import com.kakao.sdk.auth.exception.InvalidScopeException
import com.kakao.sdk.auth.exception.InvalidTokenException
import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.auth.model.AccessTokenResponse
import com.kakao.sdk.auth.model.MissingScopesErrorResponse
import com.kakao.sdk.network.ApiErrorCode
import com.kakao.sdk.network.data.ApiErrorResponse
import com.kakao.sdk.network.data.ApiException
import io.reactivex.*
import io.reactivex.functions.BiFunction
import org.reactivestreams.Publisher
import retrofit2.HttpException

/**
 * @author kevin.kang. Created on 2018. 5. 2..
 */
class ApiErrorInterceptor(private val authApiClient: AuthApiClient = AuthApiClient.instance,
                          private val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance) {

    private fun refreshAccessToken(throwableFlowable: Flowable<Throwable>): Publisher<AccessTokenResponse> {
        return throwableFlowable
                .withLatestFrom(
                        accessTokenRepo.observe().toFlowable(BackpressureStrategy.LATEST),
                        BiFunction { t1: Throwable, t2: AccessToken ->
                            if (t2.refreshToken != null && t1 is InvalidTokenException) {
                                return@BiFunction authApiClient.refreshAccessToken(refreshToken = t2.refreshToken)
                            }
                            throw t1
                        })
                .flatMap { it -> it.toFlowable() }
    }

    private fun translateError(t: Throwable): Throwable {
        try {
            if (t is HttpException) {
                val errorString = t.response().errorBody()?.string()
                val response = Gson().fromJson(errorString, ApiErrorResponse::class.java)
                when (response.code) {
                    ApiErrorCode.INVALID_TOKEN_CODE -> return InvalidTokenException(t.code(), response)
                    ApiErrorCode.INVALID_SCOPE_CODE -> {
                        val scopeErrorResponse = Gson().fromJson(errorString, MissingScopesErrorResponse::class.java)
                        return InvalidScopeException(t.code(), scopeErrorResponse)
                    }
                    ApiErrorCode.AGE_VERIFICATION_NEEDED -> return AgeVerificationException(t.code(), response)
                }
                return ApiException(t.code(), response.code, response.message)
            }
            return t
        } catch (unexpected: Throwable) {
            return unexpected
        }
    }

    fun <T> handleApiError(): SingleTransformer<T, T> {
        return SingleTransformer { it.onErrorResumeNext { Single.error(translateError(it)) }
                .retryWhen { refreshAccessToken(it) }
                .doOnError { if (it is InvalidTokenException) accessTokenRepo.clearCache() }
        }
    }

    fun handleCompletableError(): CompletableTransformer {
        return CompletableTransformer { it.onErrorResumeNext { Completable.error(translateError(it)) }
                .retryWhen { refreshAccessToken(it) }
                .doOnError { if (it is InvalidTokenException) accessTokenRepo.clearCache() }
        }
    }

    companion object {
        val instance by lazy {
            ApiErrorInterceptor()
        }
    }
}
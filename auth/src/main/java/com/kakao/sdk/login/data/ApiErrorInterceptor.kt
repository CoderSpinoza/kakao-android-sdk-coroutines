package com.kakao.sdk.login.data

import com.google.gson.Gson
import com.kakao.sdk.login.data.exception.AgeVerificationException
import com.kakao.sdk.login.data.exception.InvalidScopeException
import com.kakao.sdk.login.data.exception.InvalidTokenException
import com.kakao.sdk.network.ApiErrorCode
import com.kakao.sdk.network.data.ApiErrorResponse
import com.kakao.sdk.network.data.ApiException
import io.reactivex.*
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import org.reactivestreams.Publisher
import retrofit2.HttpException

/**
 * @author kevin.kang. Created on 2018. 5. 2..
 */
class ApiErrorInterceptor(private val authApiClient: AuthApiClient = AuthApiClient.instance,
                          private val recentToken: Observable<AccessToken> = AccessTokenRepo.instance.observe()) {

    val shouldCloseSubject = PublishSubject.create<Boolean>()
    val shouldClose = shouldCloseSubject.hide()

    private fun refreshAccessToken(throwableFlowable: Flowable<Throwable>): Publisher<AccessTokenResponse> {
        return throwableFlowable
                .withLatestFrom(
                        recentToken.toFlowable(BackpressureStrategy.LATEST),
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
                .doOnError { if (it is InvalidTokenException) shouldCloseSubject.onNext(true) }
        }
    }

    fun handleCompletableError(): CompletableTransformer {
        return CompletableTransformer { it.onErrorResumeNext { Completable.error(translateError(it)) }
                .retryWhen { refreshAccessToken(it) }
                .doOnError { if (it is InvalidTokenException) shouldCloseSubject.onNext(true) }
        }
    }

    companion object {
        val instance by lazy {
            val temp = ApiErrorInterceptor()
            temp.shouldClose.subscribe {
                AccessTokenRepo.instance.clearCache()
            }
            return@lazy temp
        }
    }
}
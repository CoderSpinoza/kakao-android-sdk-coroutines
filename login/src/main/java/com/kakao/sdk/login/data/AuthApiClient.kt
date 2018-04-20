package com.kakao.sdk.login.data

import android.util.Log
import com.kakao.sdk.login.ApiService
import com.kakao.sdk.login.domain.AccessTokenRepo
import com.kakao.sdk.login.entity.AccessTokenResponse
import com.kakao.sdk.network.ApplicationProvider
import com.kakao.sdk.network.StringSet
import com.kakao.sdk.network.Utility
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.reactivestreams.Publisher
import retrofit2.HttpException

/**
 * @author kevin.kang. Created on 2018. 3. 28..
 */
class AuthApiClient(val authApi: AuthApi = ApiService.kauth.create(AuthApi::class.java), val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance) {
    fun issueAccessToken(approvalType: String = "individual", code: String,
                         redirectUri: String = String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, StringSet.META_APP_KEY))): Single<AccessTokenResponse> {
        return authApi.issueAccessToken(Utility.getMetadata(ApplicationProvider.application, StringSet.META_APP_KEY),
                redirectUri = redirectUri,
                androidKeyHash = Utility.getKeyHash(ApplicationProvider.application)!!,
                clientSecret = Utility.getMetadata(ApplicationProvider.application, StringSet.META_CLIENT_SECRET),
                authCode = code,
                approvalType = approvalType
        )
    }

    fun refreshAccessToken(approvalType: String = "individual", refreshToken: String,
                           redirectUri: String = String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, StringSet.META_APP_KEY))): Single<AccessTokenResponse> {
        return authApi.issueAccessToken(Utility.getMetadata(ApplicationProvider.application, StringSet.META_APP_KEY),
                redirectUri = redirectUri,
                androidKeyHash = Utility.getKeyHash(ApplicationProvider.application)!!,
                clientSecret = Utility.getMetadata(ApplicationProvider.application, StringSet.META_CLIENT_SECRET),
                refreshToken = refreshToken,
                approvalType = approvalType,
                grantType = "refresh_token"
        )
    }


    fun refreshTokenObservable(throwableObservable: Observable<Throwable>): Observable<Any> {
        val publisher =
        return throwableObservable.flatMap { t ->
            val rt = accessTokenRepo.fromCache().refreshToken
            if (rt != null && t is HttpException && t.code() == 401) {
                refreshAccessToken(refreshToken = rt).toObservable()
                        .doOnNext { response -> Observable.just(accessTokenRepo.toCache(response)) }
            } else {
                Observable.error<Any>(t)
            }
        }
    }

    companion object {
        val instance = AuthApiClient()
    }
}
package com.kakao.sdk.user

import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.auth.network.ApiService
import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.user.entity.AccessTokenInfo
import com.kakao.sdk.user.entity.User
import com.kakao.sdk.user.entity.UserIdResponse
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 4. 2..
 */
class DefaultUserApiClient(val userApi: UserApi = ApiService.kapi.create(UserApi::class.java),
                           private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance,
                           private val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance): UserApiClient {
    private val shouldCloseSubject = PublishSubject.create<Boolean>()
    val shouldClose = shouldCloseSubject.hide()

    override fun me(secureReSource: Boolean): Single<User> {
        return userApi.me(secureReSource)
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun accessTokenInfo(): Single<AccessTokenInfo> {
        return userApi.accessTokenInfo()
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun logout(): Single<UserIdResponse> {
        return userApi.logout()
                .compose(apiErrorInterceptor.handleApiError())
                .doOnEvent { _, _ ->
                    accessTokenRepo.clearCache()
                    shouldCloseSubject.onNext(true)
                }
    }

    override fun unlink(): Single<UserIdResponse> {
        return userApi.unlink()
                .compose(apiErrorInterceptor.handleApiError())
                .doOnEvent { _, _ ->
                    accessTokenRepo.clearCache()
                    shouldCloseSubject.onNext(true)
                }
    }
}
package com.kakao.sdk.user.data

import com.kakao.sdk.login.data.ApiErrorInterceptor
import com.kakao.sdk.login.data.ApiService
import com.kakao.sdk.login.data.AccessTokenRepo
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

/**
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
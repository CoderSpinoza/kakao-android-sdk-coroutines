package com.kakao.sdk.login.data

import com.kakao.sdk.login.domain.UserApi
import com.kakao.sdk.login.domain.UserApiClient
import com.kakao.sdk.login.entity.user.AccessTokenInfo
import com.kakao.sdk.login.entity.user.User
import com.kakao.sdk.login.entity.UserIdResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

/**
 * @author kevin.kang. Created on 2018. 4. 2..
 */
class DefaultUserApiClient(val userApi: UserApi = ApiService.kapi.create(UserApi::class.java),
                           private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance): UserApiClient {
    private val shouldCloseSubject = PublishSubject.create<Boolean>()
    val shouldClose = shouldCloseSubject.hide()

    override fun me(secureReSource: Boolean): Single<User> {
        return userApi.me(secureReSource)
                .subscribeOn(Schedulers.io())
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun accessTokenInfo(): Single<AccessTokenInfo> {
        return userApi.accessTokenInfo()
                .subscribeOn(Schedulers.io())
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun logout(): Single<UserIdResponse> {
        return userApi.logout()
                .subscribeOn(Schedulers.io())
                .compose(apiErrorInterceptor.handleApiError())
                .doOnEvent { _, _ -> shouldCloseSubject.onNext(true) }
    }

    override fun unlink(): Single<UserIdResponse> {
        return userApi.unlink()
                .subscribeOn(Schedulers.io())
                .compose(apiErrorInterceptor.handleApiError())
                .doOnEvent { _, _ -> shouldCloseSubject.onNext(true) }
    }
}
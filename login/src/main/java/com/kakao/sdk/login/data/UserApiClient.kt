package com.kakao.sdk.login.data

import com.kakao.sdk.login.domain.AccessTokenRepo
import com.kakao.sdk.login.domain.UserApi
import com.kakao.sdk.login.entity.user.AccessTokenInfo
import com.kakao.sdk.login.entity.user.User
import com.kakao.sdk.login.entity.UserIdResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @author kevin.kang. Created on 2018. 4. 2..
 */
class UserApiClient(val userApi: UserApi = ApiService.kapi.create(UserApi::class.java)) {
    fun me(secureReSource: Boolean = true): Single<User> {
        return userApi.me(secureReSource).subscribeOn(Schedulers.io())
    }

    fun accessTokenInfo(): Single<AccessTokenInfo> {
        return userApi.accessTokenInfo().subscribeOn(Schedulers.io())
    }

    fun logout(): Single<UserIdResponse> {
        return userApi.logout().subscribeOn(Schedulers.io()).doOnSuccess { AccessTokenRepo.instance.clearCache() }
    }

    fun unlink(): Single<UserIdResponse> {
        return userApi.unlink().subscribeOn(Schedulers.io()).doOnSuccess { AccessTokenRepo.instance.clearCache() }
    }

    companion object {
        val instance = UserApiClient()
    }
}
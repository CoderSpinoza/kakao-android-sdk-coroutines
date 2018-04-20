package com.kakao.sdk.login.data

import com.kakao.sdk.login.ApiService
import com.kakao.sdk.login.entity.User
import com.kakao.sdk.login.entity.UserIdResponse
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 2018. 4. 2..
 */
class UserApiClient(val userApi: UserApi = ApiService.kapi.create(UserApi::class.java)) {
    fun me(secureReSource: Boolean = true): Single<User> {
        return userApi.me(secureReSource)
    }

    fun unlink(): Single<UserIdResponse> {
        return userApi.unlink()
    }

    companion object {
        val instance = UserApiClient()
    }
}
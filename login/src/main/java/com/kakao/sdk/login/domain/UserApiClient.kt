package com.kakao.sdk.login.domain

import com.kakao.sdk.login.data.DefaultUserApiClient
import com.kakao.sdk.login.entity.UserIdResponse
import com.kakao.sdk.login.entity.user.AccessTokenInfo
import com.kakao.sdk.login.entity.user.User
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
interface UserApiClient {
    fun me(secureReSource: Boolean = true): Single<User>

    fun accessTokenInfo(): Single<AccessTokenInfo>

    fun logout(): Single<UserIdResponse>

    fun unlink(): Single<UserIdResponse>

    companion object {
        val instance by lazy {
            val temp = DefaultUserApiClient()
            temp.shouldClose.subscribe { AccessTokenRepo.instance.clearCache() }
            return@lazy temp as UserApiClient
        }
    }
}
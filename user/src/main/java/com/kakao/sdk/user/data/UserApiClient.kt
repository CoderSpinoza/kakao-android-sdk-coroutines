package com.kakao.sdk.user.data

import com.kakao.sdk.login.domain.AccessTokenRepo
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
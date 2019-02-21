package com.kakao.sdk.user

import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.user.entity.AccessTokenInfo
import com.kakao.sdk.user.entity.User
import com.kakao.sdk.user.entity.UserIdResponse
import io.reactivex.Single

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 4. 2..
 */
class DefaultUserApiClient(val userApi: UserApi = OAuthApiFactory.kapi.create(UserApi::class.java),
                           private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance,
                           private val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance): UserApiClient {

    override suspend fun me(secureReSource: Boolean): User {
        return userApi.me(secureReSource).await()
    }

    override suspend fun accessTokenInfo(): AccessTokenInfo {
        return userApi.accessTokenInfo().await()
    }

    override suspend fun logout(): UserIdResponse {
        return userApi.logout().await()
    }

    override suspend fun unlink(): UserIdResponse {
        return userApi.unlink().await()
    }
}
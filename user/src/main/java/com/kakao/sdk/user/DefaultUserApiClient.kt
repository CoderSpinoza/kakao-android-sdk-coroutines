package com.kakao.sdk.user

import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.network.handleApiError
import com.kakao.sdk.user.entity.AccessTokenInfo
import com.kakao.sdk.user.entity.User
import com.kakao.sdk.user.entity.UserIdResponse

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 4. 2..
 */
class DefaultUserApiClient(
    val userApi: UserApi = OAuthApiFactory.kapi.create(UserApi::class.java),
    private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance,
    private val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance
) : UserApiClient {

    override suspend fun me(secureReSource: Boolean): User {
        return userApi.me(secureReSource)
                .handleApiError(apiErrorInterceptor)
    }

    override suspend fun accessTokenInfo(): AccessTokenInfo {
        return userApi.accessTokenInfo()
                .handleApiError(apiErrorInterceptor)
    }

    override suspend fun logout(): UserIdResponse {
        val response = userApi.logout()
                .handleApiError(apiErrorInterceptor)
        accessTokenRepo.clearCache()
        return response
    }

    override suspend fun unlink(): UserIdResponse {
        val response = userApi.unlink()
                .handleApiError(apiErrorInterceptor)
        accessTokenRepo.clearCache()
        return response
    }
}
package com.kakao.sdk.user

import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.user.entity.AccessTokenInfo
import com.kakao.sdk.user.entity.User
import com.kakao.sdk.user.entity.UserIdResponse
import io.reactivex.Single
import okhttp3.OkHttpClient

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
interface UserApiClient {
    suspend fun me(secureReSource: Boolean = true): User

    suspend fun accessTokenInfo(): AccessTokenInfo

    suspend fun logout(): UserIdResponse

    suspend fun unlink(): UserIdResponse

    companion object {
        val instance: UserApiClient by lazy { DefaultUserApiClient() }

        fun withClient(clientBuilder: OkHttpClient.Builder): UserApiClient {
            return DefaultUserApiClient(userApi = OAuthApiFactory.withClient(clientBuilder).create(UserApi::class.java))
        }
    }
}
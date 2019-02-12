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
    fun me(secureReSource: Boolean = true): Single<User>

    fun accessTokenInfo(): Single<AccessTokenInfo>

    fun logout(): Single<UserIdResponse>

    fun unlink(): Single<UserIdResponse>

    companion object {
        val instance: UserApiClient by lazy { DefaultUserApiClient() }

        fun withClient(clientBuilder: OkHttpClient.Builder): UserApiClient {
            return DefaultUserApiClient(userApi = OAuthApiFactory.withClient(clientBuilder).create(UserApi::class.java))
        }
    }
}
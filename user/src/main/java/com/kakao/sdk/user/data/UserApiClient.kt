package com.kakao.sdk.user.data

import com.kakao.sdk.auth.data.ApiService
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
        val instance by lazy {
            return@lazy DefaultUserApiClient()
        }

        fun withClient(clientBuilder: OkHttpClient.Builder): UserApiClient {
            return DefaultUserApiClient(userApi = ApiService.kapiWithClient(clientBuilder).create(UserApi::class.java))
        }
    }
}
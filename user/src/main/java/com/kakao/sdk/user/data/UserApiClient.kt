package com.kakao.sdk.user.data

import com.kakao.sdk.login.data.ApiService
import com.kakao.sdk.login.domain.AccessTokenRepo
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
            val temp = DefaultUserApiClient()
            temp.shouldClose.subscribe { AccessTokenRepo.instance.clearCache() }
            return@lazy temp as UserApiClient
        }

        fun withClient(clientBuilder: OkHttpClient.Builder): UserApiClient {
            val temp = DefaultUserApiClient(userApi = ApiService.kapiWithClient(clientBuilder).create(UserApi::class.java))
            temp.shouldClose.subscribe { AccessTokenRepo.instance.clearCache() }
            return temp
        }
    }
}
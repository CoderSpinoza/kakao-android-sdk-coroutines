package com.kakao.sdk.user

import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.user.entity.*
import okhttp3.OkHttpClient

/**
 * @author kevin.kang. Created on 2018. 5. 10..
 */
interface UserApiClient {
    suspend fun me(secureReSource: Boolean = true): User

    suspend fun accessTokenInfo(): AccessTokenInfo

    suspend fun logout()

    suspend fun unlink()

    suspend fun shippingAddresses(
            addressId: Long? = null,
            fromUpdateAt: Int? = null,
            pageSize: Int? = null
    ): ShippingAddresses

    suspend fun serviceTerms(): ServiceTermsResponse


    companion object {
        val instance: UserApiClient by lazy { DefaultUserApiClient() }

        fun withClient(clientBuilder: OkHttpClient.Builder): UserApiClient {
            return DefaultUserApiClient(userApi =
            OAuthApiFactory.withClient(clientBuilder).create(UserApi::class.java))
        }
    }
}
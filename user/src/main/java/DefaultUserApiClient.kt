package com.kakao.sdk.user

import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.user.entity.*
import kotlinx.coroutines.delay

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
        return apiErrorInterceptor.handleApiError {
            userApi.me(secureReSource)
        }
    }

    override suspend fun accessTokenInfo(): AccessTokenInfo {
        return apiErrorInterceptor.handleApiError {
            userApi.accessTokenInfo()
        }
    }

    override suspend fun logout() {
        apiErrorInterceptor.handleApiError { userApi.logout() }
        accessTokenRepo.clearCache()
    }

    override suspend fun unlink() {
        apiErrorInterceptor.handleApiError { userApi.unlink() }
        accessTokenRepo.clearCache()
    }

    override suspend fun shippingAddresses(addressId: Long?, fromUpdateAt: Int?, pageSize: Int?): ShippingAddresses {
        return apiErrorInterceptor.handleApiError { userApi.shippingAddresses(addressId, fromUpdateAt, pageSize) }
    }

    override suspend fun serviceTerms(): ServiceTermsResponse {
        return apiErrorInterceptor.handleApiError { userApi.serviceTerms() }
    }
}
package com.kakao.sdk.user

import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.user.entity.*

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 4. 2..
 */
class DefaultUserApiClient(
        val api: UserApi = OAuthApiFactory.kapi.create(UserApi::class.java),
        private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance,
        private val accessTokenRepo: AccessTokenRepo = AccessTokenRepo.instance
) : UserApiClient {

    override suspend fun me(): User {
        return apiErrorInterceptor.handleApiError { api.me() }
    }

    override suspend fun updateProfile(properties: Map<String, String>) {
        return apiErrorInterceptor.handleApiError { api.updateProfile(properties) }
    }

    override suspend fun accessTokenInfo(): AccessTokenInfo {
        return apiErrorInterceptor.handleApiError {
            api.accessTokenInfo()
        }
    }

    override suspend fun logout() {
        apiErrorInterceptor.handleApiError { api.logout() }
        accessTokenRepo.clearCache()
    }

    override suspend fun unlink() {
        apiErrorInterceptor.handleApiError { api.unlink() }
        accessTokenRepo.clearCache()
    }

    override suspend fun shippingAddresses(addressId: Long?, fromUpdateAt: Int?, pageSize: Int?): ShippingAddresses {
        return apiErrorInterceptor.handleApiError { api.shippingAddresses(addressId, fromUpdateAt, pageSize) }
    }

    override suspend fun serviceTerms(): ServiceTermsResponse {
        return apiErrorInterceptor.handleApiError { api.serviceTerms() }
    }
}
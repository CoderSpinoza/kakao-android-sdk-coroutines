package com.kakao.sdk.auth

import com.kakao.sdk.auth.model.AccessTokenResponse
import com.kakao.sdk.network.ApplicationProvider
import com.kakao.sdk.network.Constants
import com.kakao.sdk.network.Utility
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 2018. 5. 9..
 */
interface AuthApiClient {
    fun issueAccessToken(authCode: String,
                         clientId: String = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY),
                         redirectUri: String = String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)),
                         approvalType: String = "individual",
                         androidKeyHash: String = Utility.getKeyHash(ApplicationProvider.application),
                         clientSecret: String? = Utility.getMetadata(ApplicationProvider.application, Constants.META_CLIENT_SECRET)
    ): Single<AccessTokenResponse>

    fun refreshAccessToken(refreshToken: String,
                           clientId: String = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY),
                           redirectUri: String =String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)),
                           approvalType: String = "individual",
                           androidKeyHash: String = Utility.getKeyHash(ApplicationProvider.application),
                           clientSecret: String? = Utility.getMetadata(ApplicationProvider.application, Constants.META_CLIENT_SECRET)
    ): Single<AccessTokenResponse>

    companion object {
        val instance: AuthApiClient by lazy { DefaultAuthApiClient() }
    }
}
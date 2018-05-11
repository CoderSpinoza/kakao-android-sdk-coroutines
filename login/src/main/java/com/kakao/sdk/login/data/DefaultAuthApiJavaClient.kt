package com.kakao.sdk.login.data

import com.kakao.sdk.login.domain.AuthApiJavaClient
import com.kakao.sdk.login.domain.AuthApiClient
import com.kakao.sdk.login.entity.token.AccessTokenResponse
import com.kakao.sdk.network.ApplicationProvider
import com.kakao.sdk.network.Constants
import com.kakao.sdk.network.Utility
import io.reactivex.Single

/**
 * Since this class will just be wrapper around Kotlin version, this does not have to be tested.
 * This just fills in default arguments.
 *
 * @author kevin.kang. Created on 2018. 5. 9..
 */
class DefaultAuthApiJavaClient(private val authApiClient: AuthApiClient): AuthApiJavaClient {
    override fun issueAccessToken(authCode: String): Single<AccessTokenResponse> {
        return authApiClient.issueAccessToken(
                clientId = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY),
                redirectUri = String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)),
                approvalType = "individual",
                androidKeyHash = Utility.getKeyHash(ApplicationProvider.application),
                authCode = authCode,
                clientSecret = Utility.getMetadata(ApplicationProvider.application, Constants.META_CLIENT_SECRET)
                )
    }

    override fun refreshAccessToken(refreshToken: String): Single<AccessTokenResponse> {
        return authApiClient.refreshAccessToken(
                clientId = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY),
                redirectUri = String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)),
                approvalType = "individual",
                androidKeyHash = Utility.getKeyHash(ApplicationProvider.application),
                refreshToken = refreshToken,
                clientSecret = Utility.getMetadata(ApplicationProvider.application, Constants.META_CLIENT_SECRET)
        )

    }
}
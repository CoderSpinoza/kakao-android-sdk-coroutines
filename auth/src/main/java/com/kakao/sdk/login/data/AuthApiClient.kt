package com.kakao.sdk.login.data

import com.kakao.sdk.network.ApplicationProvider
import com.kakao.sdk.network.Constants
import com.kakao.sdk.network.Utility
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 2018. 5. 9..
 */
interface AuthApiClient {
    fun issueAccessToken(clientId: String = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY),
                         redirectUri: String = String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)),
                         approvalType: String = "individual",
                         androidKeyHash: String = Utility.getKeyHash(ApplicationProvider.application),
                         clientSecret: String? = Utility.getMetadata(ApplicationProvider.application, Constants.META_CLIENT_SECRET),
                         authCode: String
    ): Single<AccessTokenResponse>

    fun refreshAccessToken(clientId: String = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY),
                           redirectUri: String =String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)),
                           approvalType: String = "individual",
                           androidKeyHash: String = Utility.getKeyHash(ApplicationProvider.application),
                           clientSecret: String? = Utility.getMetadata(ApplicationProvider.application, Constants.META_CLIENT_SECRET),
                           refreshToken: String
    ): Single<AccessTokenResponse>

    companion object {
        val instance by lazy {
            val temp = DefaultAuthApiClient()
            temp.responseUpdates.subscribe { AccessTokenRepo.instance.toCache(it) }
            return@lazy temp as AuthApiClient
        }
    }
}
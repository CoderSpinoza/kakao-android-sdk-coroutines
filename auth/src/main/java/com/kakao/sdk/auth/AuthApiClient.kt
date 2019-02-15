package com.kakao.sdk.auth

import com.kakao.sdk.auth.model.AccessTokenResponse
import com.kakao.sdk.network.ApplicationProvider
import com.kakao.sdk.network.Constants
import com.kakao.sdk.network.Utility
import io.reactivex.Single

/**
 * Kakao OAuth API를 호출할 수 있는 클라이언트.
 *
 * @author kevin.kang. Created on 2018. 5. 9..
 */
interface AuthApiClient {
    /**
     * [AuthCodeService] 를 이용하여 발급 받은 authorization code를 사용하여 액세스 토큰을 발급한다.
     *
     * @param authCode authorization code
     *
     * @return [Single] instance that will emit [AccessTokenResponse].
     */
    fun issueAccessToken(authCode: String,
                         clientId: String = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)!!,
                         redirectUri: String = String.format("kakao%s://oauth", Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)),
                         approvalType: String = "individual",
                         androidKeyHash: String = Utility.getKeyHash(ApplicationProvider.application),
                         clientSecret: String? = Utility.getMetadata(ApplicationProvider.application, Constants.META_CLIENT_SECRET)
    ): Single<AccessTokenResponse>

    /**
     * 기존에 [issueAccessToken] 또는 이 메소드를 사용하여 발급 받은 리프레시 토큰으로 액세스 토큰을 갱신한다.
     *
     * @param refreshToken 리프레시 토큰
     *
     * @return [Single] instance that will emit [AccessTokenResponse]
     */
    fun refreshAccessToken(refreshToken: String,
                           clientId: String = Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY)!!,
                           approvalType: String = "individual",
                           androidKeyHash: String = Utility.getKeyHash(ApplicationProvider.application),
                           clientSecret: String? = Utility.getMetadata(ApplicationProvider.application, Constants.META_CLIENT_SECRET)
    ): Single<AccessTokenResponse>

    companion object {
        val instance: AuthApiClient by lazy { DefaultAuthApiClient() }
    }
}
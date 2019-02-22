package com.kakao.sdk.auth

import com.kakao.sdk.auth.model.AccessTokenResponse
import com.kakao.sdk.common.KakaoSdkProvider

/**
 * Kakao OAuth API 를 호출할 수 있는 클라이언트.
 *
 * @author kevin.kang. Created on 2018. 5. 9..
 */
interface AuthApiClient {
    /**
     * [AuthCodeService] 를 이용하여 발급 받은 authorization code 를 사용하여 액세스 토큰을 발급한다.
     *
     * @param authCode authorization code
     *
     * @return [AccessTokenResponse].
     */
    suspend fun issueAccessToken(authCode: String,
                         clientId: String = KakaoSdkProvider.applicationContextInfo.clientId,
                         redirectUri: String = String.format("kakao%s://oauth", KakaoSdkProvider.applicationContextInfo.clientId),
                         approvalType: String = "individual",
                         androidKeyHash: String = KakaoSdkProvider.applicationContextInfo.signingKeyHash,
                         clientSecret: String? = KakaoSdkProvider.applicationContextInfo.clientSecret
    ): AccessTokenResponse

    /**
     * 기존에 [issueAccessToken] 또는 이 메소드를 사용하여 발급 받은 리프레시 토큰으로 액세스 토큰을 갱신한다.
     *
     * @param refreshToken 리프레시 토큰
     *
     * @return [AccessTokenResponse]
     */
    suspend fun refreshAccessToken(refreshToken: String,
                           clientId: String = KakaoSdkProvider.applicationContextInfo.clientId,
                           approvalType: String = "individual",
                           androidKeyHash: String = KakaoSdkProvider.applicationContextInfo.signingKeyHash,
                           clientSecret: String? = KakaoSdkProvider.applicationContextInfo.clientSecret
    ): AccessTokenResponse

    companion object {
        val instance: AuthApiClient by lazy { DefaultAuthApiClient() }
    }
}
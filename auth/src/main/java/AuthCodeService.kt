package com.kakao.sdk.auth

import android.content.Context
import com.kakao.sdk.common.KakaoSdkProvider

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
interface AuthCodeService {
    /**
     * 별도의 scope 설정 없이 authorization code 를 발급 받는다.
     *
     * @param context instance for starting an activity
     *
     * @return [Unit]
     */
    fun requestAuthCode(context: Context,
                        clientId: String = KakaoSdkProvider.applicationContextInfo.clientId,
                        redirectUri: String = String.format("kakao%s://oauth", KakaoSdkProvider.applicationContextInfo.clientId),
                        approvalType: String = "individual",
                        kaHeader: String = KakaoSdkProvider.applicationContextInfo.kaHeader
    )

    /**
     * 유저의 scope 들을 업데이트할 수 있는 authorization code 를 발급받는다.
     * API 호출 시 [com.kakao.sdk.auth.exception.InvalidScopeException] 이 발생한 경우 해당 예외의
     * [com.kakao.sdk.auth.model.MissingScopesErrorResponse.requiredScopes] 를 사용하여 호출한다.
     *
     * @param context [Context] instance for starting an activity
     * @param scopes list of scopes to be updated
     *
     * @return [Single] that will emit authorization code
     */
    suspend fun requestAuthCode(context: Context,
                        scopes: List<String>,
                        clientId: String = KakaoSdkProvider.applicationContextInfo.clientId,
                        redirectUri: String = String.format("kakao%s://oauth", KakaoSdkProvider.applicationContextInfo.clientId),
                        approvalType: String = "individual",
                        kaHeader: String = KakaoSdkProvider.applicationContextInfo.kaHeader
    ): String

    companion object {
        val instance by lazy {
            DefaultAuthCodeService(AccessTokenRepo.instance.observe()) as AuthCodeService
        }
    }
}
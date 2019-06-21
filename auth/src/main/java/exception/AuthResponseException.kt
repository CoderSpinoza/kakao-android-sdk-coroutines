package com.kakao.sdk.auth.exception

import com.kakao.sdk.auth.model.AuthErrorResponse
import com.kakao.sdk.common.KakaoGsonFactory

/**
 * Kakao OAuth API 호출 시 에러 응답을 wrapping 하는 exception
 *
 * @see com.kakao.sdk.auth.AuthApiClient
 *
 * @property response actual [AuthErrorResponse] from OAuth server
 *
 * @since 2.0.0
 * @author kevin.kang. Created on 2018. 5. 5..
 */
class AuthResponseException(
        val httpStatus: Int,
        val response: AuthErrorResponse
) : AuthException(response.errorDescription ?: "No error description") {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}
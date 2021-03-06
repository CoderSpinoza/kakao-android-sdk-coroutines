package com.kakao.sdk.auth.model

import com.kakao.sdk.common.KakaoGsonFactory

/**
 * 카카오 OAuth API 호출 시 에러 응답
 *
 * @see com.kakao.sdk.auth.AuthApiClient
 *
 * @property error invalid_grant 등 어떤 에러인지 나타내주는 스트링 값
 * @property errorDescription 자세한 에러 설명
 *
 * @author kevin.kang. Created on 2018. 5. 5..
 */
data class AuthErrorResponse(val error: String?, val errorDescription: String?) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}
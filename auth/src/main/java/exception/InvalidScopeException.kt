package com.kakao.sdk.auth.exception

import com.kakao.sdk.auth.model.MissingScopesErrorResponse
import com.kakao.sdk.network.data.ApiException

/**
 * API 를 호출할 수 있도록 유저가 아직 동의하지 않은 경우의 exception
 *
 * @see com.kakao.sdk.auth.AuthCodeService
 *
 * @author kevin.kang. Created on 2018. 5. 5..
 */
class InvalidScopeException(
    override val httpStatus: Int,
    val errorResponse: MissingScopesErrorResponse
) : ApiException(httpStatus, errorResponse.code, errorResponse.message) {
    override fun toString(): String {
        return "$httpStatus: $errorResponse"
    }
}
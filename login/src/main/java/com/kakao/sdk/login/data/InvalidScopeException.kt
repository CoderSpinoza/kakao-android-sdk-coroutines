package com.kakao.sdk.login.data

import com.kakao.sdk.network.data.ApiException

/**
 * @author kevin.kang. Created on 2018. 5. 5..
 */
class InvalidScopeException(override val httpStatus: Int, val errorResponse: MissingScopesErrorResponse) :
        ApiException(httpStatus, errorResponse.code, errorResponse.message) {
    override fun toString(): String {
        return "${httpStatus}: ${errorResponse}"
    }
}
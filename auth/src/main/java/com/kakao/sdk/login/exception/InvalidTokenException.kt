package com.kakao.sdk.login.exception

import com.kakao.sdk.network.data.ApiErrorResponse
import com.kakao.sdk.network.data.ApiException

/**
 * @author kevin.kang. Created on 2018. 5. 2..
 */
class InvalidTokenException(httpStatus: Int, errorResponse: ApiErrorResponse) :
        ApiException(httpStatus, errorResponse.code, errorResponse.message) {
}
package com.kakao.sdk.login.data.exception

import com.kakao.sdk.network.data.ApiErrorResponse
import com.kakao.sdk.network.data.ApiException

/**
 * @author kevin.kang. Created on 2018. 5. 9..
 */
class AgeVerificationException(httpStatus: Int, errorResponse: ApiErrorResponse):
        ApiException(httpStatus, errorResponse.code, errorResponse.message) {
}
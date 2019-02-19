package com.kakao.sdk.auth.exception

import com.kakao.sdk.network.data.ApiErrorResponse
import com.kakao.sdk.network.data.ApiException

/**
 * 연령 인증 API 호출 시 에러 응답을 wrapping 하는 exception
 *
 * @author kevin.kang. Created on 2018. 5. 9..
 */
class AgeVerificationException(httpStatus: Int, errorResponse: ApiErrorResponse):
        ApiException(httpStatus, errorResponse.code, errorResponse.message)
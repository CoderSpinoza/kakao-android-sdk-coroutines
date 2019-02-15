package com.kakao.sdk.auth.exception

import com.kakao.sdk.network.data.ApiErrorResponse
import com.kakao.sdk.network.data.ApiException

/**
 * 카카오 API 호출 시 사용된 액세스 토큰이 만료되었거나 유저가 무효화시킨 경우의 exception
 *
 * @author kevin.kang. Created on 2018. 5. 2..
 */
class InvalidTokenException(httpStatus: Int, errorResponse: ApiErrorResponse) :
        ApiException(httpStatus, errorResponse.code, errorResponse.message)
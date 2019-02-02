package com.kakao.sdk.auth.exception

import com.kakao.sdk.auth.model.AuthErrorResponse

/**
 * @author kevin.kang. Created on 2018. 5. 5..
 */
class AuthException(httpStatus: Int, response: AuthErrorResponse) : RuntimeException(response.errorDescription) {
}
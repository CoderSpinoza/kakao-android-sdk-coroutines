package com.kakao.sdk.login.exception

import com.kakao.sdk.login.entity.AuthErrorResponse

/**
 * @author kevin.kang. Created on 2018. 5. 5..
 */
class AuthException(httpStatus: Int, response: AuthErrorResponse) : RuntimeException(response.errorDescription) {
}
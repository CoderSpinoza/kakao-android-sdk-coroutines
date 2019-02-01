package com.kakao.sdk.login.data.exception

import com.kakao.sdk.login.data.AuthErrorResponse

/**
 * @author kevin.kang. Created on 2018. 5. 5..
 */
class AuthException(httpStatus: Int, response: AuthErrorResponse) : RuntimeException(response.errorDescription) {
}
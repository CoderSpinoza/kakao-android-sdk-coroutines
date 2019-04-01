package com.kakao.sdk.auth.exception

/**
 * @author kevin.kang. Created on 18/03/2019..
 */
class TalkAuthCodeException(val errorType: String, val errorMessage: String): AuthException(errorMessage)
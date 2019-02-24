package com.kakao.sdk.auth.exception

class AuthWebViewException(val errorCode: Int, val errorMessage: String, requestedUrl: String): AuthException(errorMessage)
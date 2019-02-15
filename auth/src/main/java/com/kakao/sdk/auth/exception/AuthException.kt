package com.kakao.sdk.auth.exception

import java.lang.RuntimeException

open class AuthException : RuntimeException {
    constructor(): super()
    constructor(errorMessage: String) : super(errorMessage)
}
package com.kakao.sdk.network.data

/**
 * @author kevin.kang. Created on 2018. 5. 2..
 */
open class ApiException(
    open val httpStatus: Int,
    val errorCode: Int,
    message: String
) : RuntimeException(message)
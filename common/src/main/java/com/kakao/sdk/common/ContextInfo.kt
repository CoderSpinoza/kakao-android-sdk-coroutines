package com.kakao.sdk.common

import com.google.gson.JsonObject

/**
 * @author kevin.kang. Created on 19/02/2019..
 */
interface ContextInfo {
    val kaHeader: String
    val signingKeyHash: String
    val extras: JsonObject
}
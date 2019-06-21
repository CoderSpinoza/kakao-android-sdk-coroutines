package com.kakao.sdk.auth.model

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.common.KakaoGsonFactory

/**
 * @author kevin.kang. Created on 2018. 4. 3..
 */
class MissingScopesErrorResponse(
        val code: Int,
        @SerializedName("msg") val message: String,
        val apiType: String,
        val requiredScopes: List<String>,
        val allowedScopes: List<String>
) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}
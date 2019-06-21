package com.kakao.sdk.user.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.common.KakaoGsonFactory

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class AccessTokenInfo(
        @SerializedName("appId") val appId: Long,
        val id: Long,
        @SerializedName("expiresInMillis") val expiresInMillis: Long
) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}
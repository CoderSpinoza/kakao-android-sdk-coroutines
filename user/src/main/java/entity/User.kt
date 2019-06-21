package com.kakao.sdk.user.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.common.KakaoGsonFactory

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class User(
        val id: Long,
        val hasSignedUp: Boolean = true,
        val properties: Map<String, String>,
        @SerializedName(Constants.FOR_PARTNER) val forPartners: Map<String, String>,
        val kakaoAccount: UserAccount
) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}
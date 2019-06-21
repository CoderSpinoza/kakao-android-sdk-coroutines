package com.kakao.sdk.auth.model

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.auth.Constants
import com.kakao.sdk.common.KakaoGsonFactory

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
class AccessTokenResponse(
        val accessToken: String,
        val refreshToken: String? = null,
        @SerializedName(Constants.EXPIRES_IN) val accessTokenExpiresIn: Long,
        val refreshTokenExpiresIn: Long?,
        val tokenType: String,
        @SerializedName(Constants.SCOPE) val scopes: String? = null
) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}
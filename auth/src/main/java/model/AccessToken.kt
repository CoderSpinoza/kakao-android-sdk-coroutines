package com.kakao.sdk.auth.model

import com.kakao.sdk.common.KakaoGsonFactory
import java.util.Date

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class AccessToken(
        val accessToken: String? = null,
        val accessTokenExpiresAt: Date? = null,
        val refreshToken: String? = null,
        val refreshTokenExpiresAt: Date? = null,
        val scopes: List<String>? = null
) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}
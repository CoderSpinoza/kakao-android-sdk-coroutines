package com.kakao.sdk.login.entity

import com.google.gson.GsonBuilder
import java.util.Date

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class AccessToken(val accessToken: String?,
                       val refreshToken: String?,
                       val accessTokenExpiresAt: Date?,
                       val refreshTokenExpiresAt: Date?) {
    fun hasValidAccessToken(): Boolean {
        return Date().before(accessTokenExpiresAt)
    }

    fun hasValidRefreshToken(): Boolean {
        return Date().before(refreshTokenExpiresAt)
    }

    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
package com.kakao.sdk.login.entity

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class AccessTokenResponse(@SerializedName("access_token") val accessToken: String,
                               @SerializedName("refresh_token") val refreshToken: String? = null,
                               @SerializedName("expires_in") val accessTokenExpiresIn: Long,
                               @SerializedName("refresh_token_expires_in") val refreshTokenExpiresIn: Long,
                               @SerializedName("token_type") val tokenType: String,
                               @SerializedName("scope") val scope: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
package com.kakao.sdk.login.data

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class AccessTokenResponse(@SerializedName("access_token") val accessToken: String,
                               @SerializedName("refresh_token") val refreshToken: String,
                               @SerializedName("expires_in") val accessTokenExpiresIn: Int,
                               @SerializedName("refresh_token_expires_in") val refreshTokenExpiresIn: Int?,
                               @SerializedName("token_type") val tokenType: String,
                               @SerializedName("scope") val scope: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
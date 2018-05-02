package com.kakao.sdk.login.entity.token

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.kakao.sdk.login.Constants

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class AccessTokenResponse(@SerializedName(Constants.ACCESS_TOKEN) val accessToken: String,
                               @SerializedName(Constants.REFRESH_TOKEN) val refreshToken: String? = null,
                               @SerializedName(Constants.EXPIRES_IN) val accessTokenExpiresIn: Long,
                               @SerializedName(Constants.REFRESH_TOKEN_EXPIRES_IN) val refreshTokenExpiresIn: Long?,
                               @SerializedName(Constants.TOKEN_TYPE) val tokenType: String,
                               @SerializedName(Constants.SCOPE) val scopes: String? = null) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
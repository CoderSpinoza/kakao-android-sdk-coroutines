package com.kakao.sdk.login.entity

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class AccessTokenInfo(val appId: Long?,
                           val expiresInMillis: Long?,
                           @SerializedName("id") val userId: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
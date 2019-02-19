package com.kakao.sdk.user.entity

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class AccessTokenInfo(@SerializedName("appId") val appId: Long,
                           val id: Long,
                           @SerializedName("expiresInMillis") val expiresInMillis: Long,
                           val kaccountId: Long?) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
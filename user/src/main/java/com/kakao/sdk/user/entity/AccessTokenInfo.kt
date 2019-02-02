package com.kakao.sdk.user.entity

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class AccessTokenInfo(val appId: Long,
                           val id: Long,
                           val expiresInMillis: Long,
                           @SerializedName("kaccount_id") val kaccountId: Long?) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
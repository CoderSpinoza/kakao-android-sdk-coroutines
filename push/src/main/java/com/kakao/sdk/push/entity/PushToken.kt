package com.kakao.sdk.push.entity

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
data class PushToken(@SerializedName("user_id") val userId: String,
                     @SerializedName("device_id") val deviceId: String,
                     @SerializedName("push_type") val pushType: String,
                     @SerializedName("push_token") val pushToken: String,
                     @SerializedName("created_at") val createdAt: String,
                     @SerializedName("updated_at") val updatedAt: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
package com.kakao.sdk.push

import com.google.gson.GsonBuilder

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
data class PushToken(val userId: String,
                     val deviceId: String,
                     val pushType: String,
                     val pushToken: String,
                     val createdAt: String,
                     val updatedAt: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
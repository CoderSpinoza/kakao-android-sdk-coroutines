package com.kakao.sdk.push

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
data class PushMessage(@SerializedName("gcm") val fcmMessage: FcmMessage) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
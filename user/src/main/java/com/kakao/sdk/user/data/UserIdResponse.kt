package com.kakao.sdk.user.data

import com.google.gson.GsonBuilder

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class UserIdResponse(val id: Long) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
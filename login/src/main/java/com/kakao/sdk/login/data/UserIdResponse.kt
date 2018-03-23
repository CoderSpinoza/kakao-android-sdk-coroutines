package com.kakao.sdk.login.data

import com.google.gson.GsonBuilder

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class UserIdResponse(val id: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
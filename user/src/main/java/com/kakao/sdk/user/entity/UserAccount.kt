package com.kakao.sdk.user.entity

import com.google.gson.GsonBuilder

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
data class UserAccount(
    val hasEmail: Boolean,
    val isEmailVerified: Boolean?,
    val email: String?,
    val isKakaotalkUser: Boolean?,
    val hasPhoneNumber: Boolean?,
    val phoneNumber: String?,
    val displayId: String
) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
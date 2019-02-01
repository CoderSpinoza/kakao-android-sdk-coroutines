package com.kakao.sdk.user.data

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.kakao.sdk.auth.Constants

/**
 * @author kevin.kang. Created on 2018. 4. 25..
 */
data class UserAccount(@SerializedName(Constants.HAS_EMAIL) val hasEmail: Boolean,
                       @SerializedName(Constants.IS_EMAIL_VERIFIED) val isEmailVerified: Boolean?,
                       val email: String?,
                       @SerializedName(Constants.IS_KAKAOTALK_USER) val isKakaotalkUser: Boolean?,
                       @SerializedName(Constants.HAS_PHONE_NUMBER) val hasPhoneNumber: Boolean?,
                       @SerializedName(Constants.PHONE_NUMBER) val phoneNumber: String?,
                       @SerializedName(Constants.DISPLAY_ID) val displayId: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
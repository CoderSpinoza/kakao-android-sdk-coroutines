package com.kakao.sdk.user.data

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.kakao.sdk.login.Constants

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
data class User(val id: Long,
                @SerializedName(Constants.HAS_SIGNED_UP)  val hasSignedUp: Boolean = true,
                val properties: Map<String, String>,
                @SerializedName(Constants.FOR_PARTNER) val forPartners: Map<String, String>,
                @SerializedName(Constants.KAKAO_ACCOUNT) val kakaoAccount: UserAccount) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
package com.kakao.sdk.kakaostory.data

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
class StoryProfile(@SerializedName("nickName") val nickname: String,
                   @SerializedName("profileImageURL") val profileImageUrl: String,
                   @SerializedName("thumbnailURL") val thumbnailUrl: String,
                   @SerializedName("bgImageURL") val bgImageUrl: String,
                   val permalink: String,
                   val birthday: String,
                   val birthdayType: BirthdayType) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

    enum class BirthdayType(val displaySymbol: String) {
        /**
         * 양력 생일
         */
        SOLAR("+"),
        /**
         * 음력 생일
         */
        LUNAR("-");
    }
}
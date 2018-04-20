package com.kakao.sdk.kakaotalk.entity

import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class TalkProfile(@SerializedName("nickName") val nickname: String,
                       @SerializedName("profileImageURL") val profileImageUrl: String,
                       @SerializedName("thumbnailURL") val thumbnailUrl: String,
                       val countryISO: String)
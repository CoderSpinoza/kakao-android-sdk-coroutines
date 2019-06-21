package com.kakao.sdk.kakaotalk.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.common.KakaoGsonFactory

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
data class FriendsResponse(
        val totalCount: Int,
        @SerializedName("elements") val friends: List<Friend>,
        val beforeUrl: String?,
        val afterUrl: String?
) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }
}
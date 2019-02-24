package com.kakao.sdk.friends.entity

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
data class FriendsResponse(val totalCount: Int,
                           @SerializedName("elements") val friends: List<Friend>,
                           val beforeUrl: String?,
                           val afterUrl: String?,
                           val resultId: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
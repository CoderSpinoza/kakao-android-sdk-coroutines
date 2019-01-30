package com.kakao.sdk.friends.data

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.kakao.sdk.friends.data.Friend

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
data class FriendsResponse(@SerializedName("total_count") val totalCount: Int,
                           @SerializedName("elements") val friends: List<Friend>,
                           @SerializedName("before_url") val beforeUrl: String?,
                           @SerializedName("after_url") val afterUrl: String?,
                           @SerializedName("result_id") val resultId: String) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
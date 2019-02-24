package com.kakao.sdk.friends.entity

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
data class Friend(
    val uuid: String,
    @SerializedName("id") val userId: Long,
    val serviceUserId: Long,
    @SerializedName("app_registered") val isAppRegistered: Boolean,
    val profileNickname: String,
    val profileThumbnailImage: String,
    val talkOs: String,
    @SerializedName("allowed_msg") val isAllowedMsg: Boolean,
    val relation: FriendRelation
) {

    enum class Relation(val value: String) {
        @SerializedName("N/A")
        NONE("N/A"),
        @SerializedName("friend")
        FRIEND("friend"),
        @SerializedName("no_friend")
        NOT_FRIEND("no_friend");

        override fun toString(): String {
            return this.value
        }
    }

    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }
}
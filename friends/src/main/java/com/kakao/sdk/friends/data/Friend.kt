package com.kakao.sdk.friends.data

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.kakao.sdk.friends.entity.FriendRelation

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
data class Friend(val uuid: String,
                  @SerializedName("id") val userId: Long,
                  @SerializedName("service_user_id") val serviceUserId: Long,
                  @SerializedName("app_registered") val isAppRegistered: Boolean,
                  @SerializedName("profile_nickname") val profileNickname: String,
                  @SerializedName("profile_thumbnail_image") val profileThumbnailImage: String,
                  @SerializedName("talk_os") val talkOs: String,
                  @SerializedName("allowed_msg") val isAllowedMsg: Boolean,
                  val relation: FriendRelation) {

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
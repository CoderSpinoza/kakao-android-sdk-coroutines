package com.kakao.sdk.kakaostory.data

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class Story(val id: String,
                 val url: String,
                 @SerializedName("media_type") val mediaType: String,
                 @SerializedName("created_at") val createdAt: String,
                 @SerializedName("comment_count") val commentCount: String,
                 @SerializedName("like_count") val likeCount: String,
                 val content: String,
                 val permission: String,
                 @SerializedName("media") val imageInfos: List<StoryImage>?,
                 val likes: List<StoryLike>,
                 val comments: List<StoryComment>) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

    enum class Permission(val value: String) {
        /**
         * 알수 없는 공개 범위
         */
        @SerializedName("UNKNOWN")
        UNKNOWN("UNKNOWN"),
        /**
         * 전체공개
         */
        @SerializedName("A")
        PUBLIC("A"),
        /**
         * 친구공개
         */
        @SerializedName("F")
        FRIEND("F"),
        /**
         * 나만보기
         */
        @SerializedName("M")
        ONLY_ME("M");

        override fun toString(): String {
            return value
        }
    }
}
package com.kakao.sdk.kakaostory.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.common.KakaoGsonFactory

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
data class Story(
        val id: String,
        val url: String,
        val mediaType: String,
        val createdAt: String,
        val commentCount: String,
        val likeCount: String,
        val content: String,
        val permission: Permission?,
        @SerializedName("media") val images: List<StoryImage>?,
        val likes: List<StoryLike>,
        val comments: List<StoryComment>
) {
    override fun toString(): String {
        return KakaoGsonFactory.pretty.toJson(this)
    }

    enum class Permission(val value: String) {
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
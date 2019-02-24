package com.kakao.sdk.message.template.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.Constants

/**
 * 좋아요 수, 댓글 수 등의 소셜 정보를 표현하기 위해 사용되는 오브젝트입니다.
 * @param likeCount 콘텐츠의 좋아요 수
 * @param commentCount 콘텐츠의 댓글 수
 * @param sharedCount 콘텐츠의 공유 수
 * @param viewCount 콘텐츠의 조회 수
 * @param subscriberCount 콘텐츠의 구독 수
 *
 * @author kevin.kang
 */
data class SocialObject(
    @SerializedName(Constants.LIKE_COUNT) val likeCount: Int? = null,
    @SerializedName(Constants.COMMENT_COUNT) val commentCount: Int? = null,
    @SerializedName(Constants.SHARED_COUNT) val sharedCount: Int? = null,
    @SerializedName(Constants.VIEW_COUNT) val viewCount: Int? = null,
    @SerializedName(Constants.SUBSCRIBER_COUNT) val subscriberCount: Int? = null
)
package com.kakao.sdk.message.template.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.message.template.Constants

data class SocialObject(@SerializedName(Constants.LIKE_COUNT) val likeCount: Int? = null,
                        @SerializedName(Constants.COMMENT_COUNT) val commentCount: Int? = null,
                        @SerializedName(Constants.SHARED_COUNT) val sharedCount: Int? = null,
                        @SerializedName(Constants.VIEW_COUNT) val viewCount: Int? = null,
                        @SerializedName(Constants.SUBSCRIBER_COUNT) val subscriberCount: Int? = null)
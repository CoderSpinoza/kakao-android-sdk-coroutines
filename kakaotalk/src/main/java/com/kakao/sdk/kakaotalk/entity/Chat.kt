package com.kakao.sdk.kakaotalk.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaotalk.Constants

/**
 * @author kevin.kang. Created on 2018. 4. 28..
 */
data class Chat(@SerializedName(Constants.ID) val id: Long,
                @SerializedName(Constants.TITLE) val title: String,
                @SerializedName(Constants.IMAGE_URL) val imageUrl: String?,
                @SerializedName(Constants.MEMBER_COUNT) val memberCount: Int,
                @SerializedName(Constants.DISPLAY_MEMBER_IMAGES) val displayMemberImages: List<String>,
                @SerializedName(Constants.CHAT_TYPE) val chatType: String)
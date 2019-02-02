package com.kakao.sdk.kakaotalk.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaotalk.Constants

/**
 * @author kevin.kang. Created on 2018. 4. 28..
 */
data class ChatListResponse(@SerializedName(Constants.TOTAL_COUNT) val totalCount: Int,
                            @SerializedName(Constants.ELEMENTS) val chatList: List<Chat>,
                            @SerializedName(Constants.BEFORE_URL) val beforeUrl: String,
                            @SerializedName(Constants.AFTER_URL) val afterUrl: String)
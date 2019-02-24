package com.kakao.sdk.kakaotalk.entity

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.kakaotalk.Constants

/**
 * @author kevin.kang. Created on 2018. 4. 28..
 */
data class ChatListResponse(val totalCount: Int,
                            @SerializedName(Constants.ELEMENTS) val chatList: List<Chat>,
                            val beforeUrl: String,
                            val afterUrl: String)
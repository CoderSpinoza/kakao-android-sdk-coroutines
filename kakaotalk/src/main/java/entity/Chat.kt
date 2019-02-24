package com.kakao.sdk.kakaotalk.entity

/**
 * @author kevin.kang. Created on 2018. 4. 28..
 */
data class Chat(val id: Long,
                val title: String,
                val imageUrl: String?,
                val memberCount: Int,
                val displayMemberImages: List<String>,
                val chatType: String)
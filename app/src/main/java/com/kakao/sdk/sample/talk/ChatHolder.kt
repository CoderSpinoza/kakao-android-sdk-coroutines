package com.kakao.sdk.sample.talk

import android.support.v7.widget.RecyclerView
import android.util.Log
import com.bumptech.glide.Glide
import com.kakao.sdk.friends.entity.Friend
import com.kakao.sdk.kakaotalk.entity.Chat
import com.kakao.sdk.sample.databinding.ItemChatBinding
import com.kakao.sdk.sample.databinding.ItemFriendBinding

/**
 * @author kevin.kang. Created on 2018. 4. 23..
 */
class ChatHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(chat: Chat) {
        binding.chat = chat
        binding.executePendingBindings()
    }
}
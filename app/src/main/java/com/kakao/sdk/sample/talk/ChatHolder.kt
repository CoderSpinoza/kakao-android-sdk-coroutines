package com.kakao.sdk.sample.talk

import androidx.recyclerview.widget.RecyclerView
import com.kakao.sdk.kakaotalk.entity.Chat
import com.kakao.sdk.sample.databinding.ItemChatBinding

/**
 * @author kevin.kang. Created on 2018. 4. 23..
 */
class ChatHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(chat: Chat) {
        binding.chat = chat
        binding.executePendingBindings()
    }
}
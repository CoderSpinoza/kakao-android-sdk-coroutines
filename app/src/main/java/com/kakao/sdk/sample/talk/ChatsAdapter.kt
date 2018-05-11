package com.kakao.sdk.sample.talk

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kakao.sdk.friends.entity.Friend
import com.kakao.sdk.kakaotalk.entity.Chat
import com.kakao.sdk.sample.databinding.ItemChatBinding
import com.kakao.sdk.sample.databinding.ItemFriendBinding

/**
 * @author kevin.kang. Created on 2018. 4. 23..
 */
class ChatsAdapter(var chats: List<Chat>) : RecyclerView.Adapter<ChatHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatBinding.inflate(inflater, parent, false)
        return ChatHolder(binding)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.bind(chats[position])
    }
}
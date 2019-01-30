package com.kakao.sdk.sample.talk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kakao.sdk.kakaotalk.data.Chat
import com.kakao.sdk.sample.databinding.ItemChatBinding
import io.reactivex.subjects.PublishSubject

/**
 * @author kevin.kang. Created on 2018. 4. 23..
 */
class ChatAdapter(var chats: List<Chat>) : RecyclerView.Adapter<ChatHolder>() {
    private val clickSubject = PublishSubject.create<Chat>()
    val clickEvents = clickSubject.hide()

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
        holder.binding.root.setOnClickListener { clickSubject.onNext(chats[position]) }
    }
}
package com.kakao.sdk.sample.friends

import android.support.v7.widget.RecyclerView
import com.kakao.sdk.friends.entity.Friend
import com.kakao.sdk.sample.databinding.ItemFriendBinding

/**
 * @author kevin.kang. Created on 2018. 5. 11..
 */
class FriendHolder(val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(friend: Friend) {
        binding.friend = friend
        binding.executePendingBindings()
    }
}
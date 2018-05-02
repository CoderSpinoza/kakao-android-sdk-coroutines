package com.kakao.sdk.sample.talk

import android.support.v7.widget.RecyclerView
import android.util.Log
import com.bumptech.glide.Glide
import com.kakao.sdk.friends.entity.Friend
import com.kakao.sdk.sample.databinding.ItemFriendBinding

/**
 * @author kevin.kang. Created on 2018. 4. 23..
 */
class FriendHolder(private val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(friend: Friend) {
        binding.friend = friend

        if (friend.profileThumbnailImage != "") {
            Glide.with(binding.root.context).load(friend.profileThumbnailImage).into(binding.profileImageView)
        }
        binding.executePendingBindings()
    }
}
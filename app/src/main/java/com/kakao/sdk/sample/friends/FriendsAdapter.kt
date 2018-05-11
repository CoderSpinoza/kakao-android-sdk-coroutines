package com.kakao.sdk.sample.friends

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kakao.sdk.friends.entity.Friend
import com.kakao.sdk.sample.databinding.ItemFriendBinding

/**
* @author kevin.kang. Created on 2018. 5. 11..
*/
class FriendsAdapter(var friends: List<Friend>): RecyclerView.Adapter<FriendHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendBinding.inflate(inflater, parent, false)
        return FriendHolder(binding)
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        holder.bind(friends[position])
    }
}
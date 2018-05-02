package com.kakao.sdk.sample.talk

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kakao.sdk.friends.data.FriendsApiClient
import com.kakao.sdk.sample.BaseFragment
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FragmentTalkBinding

/**
 *
 */
class TalkFragment : BaseFragment() {
    private lateinit var binding: FragmentTalkBinding
    private lateinit var friendsAdapter: FriendsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)
        binding.talkViewModel = TalkViewModel(FriendsApiClient.instance)


        friendsAdapter = FriendsAdapter(emptyList())
        binding.talkFriendsList.adapter = friendsAdapter

        val talkViewModel = binding.talkViewModel
        talkViewModel?.friends?.observe(this, Observer { friends ->
            friendsAdapter.friends = friends!!
            friendsAdapter.notifyDataSetChanged()
        })
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (this.isVisible and isVisibleToUser) {
            binding.talkViewModel?.loadFriends()
        }
    }
}

package com.kakao.sdk.sample.talk

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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)
        binding.talkViewModel = TalkViewModel(FriendsApiClient.instance)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (this.isVisible and isVisibleToUser) {
            binding.talkViewModel?.loadFriends()
        }
    }
}

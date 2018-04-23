package com.kakao.sdk.sample.story

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kakao.sdk.kakaostory.data.KakaoStoryApiClient
import com.kakao.sdk.sample.BaseFragment
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FragmentStoryBinding

/**
 *
 */
class StoryFragment : BaseFragment() {
    private lateinit var binding: FragmentStoryBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story, container, false)
        val view = binding.root

        binding.setLifecycleOwner(this)
        binding.storyViewModel = StoryViewModel(KakaoStoryApiClient.instance)
        binding.storyViewModel?.isStoryUser?.observe(this, Observer {
            if (it != null && it) {
                binding.storyViewModel?.getMyStories()
            } else {
                Log.e("StoryFragment", "This user is not story user")
            }
        })
        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (this.isVisible and isVisibleToUser) {
            binding.storyViewModel?.isStoryUser()
        }
    }
}

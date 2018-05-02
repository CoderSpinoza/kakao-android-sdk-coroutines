package com.kakao.sdk.sample.story

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kakao.sdk.kakaostory.data.KakaoStoryApiClient
import com.kakao.sdk.login.data.AuthApiClient
import com.kakao.sdk.login.domain.AccessTokenRepo
import com.kakao.sdk.login.presentation.AuthCodeService
import com.kakao.sdk.sample.BaseFragment
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FragmentStoryBinding
import io.reactivex.schedulers.Schedulers

/**
 *
 */
class StoryFragment : BaseFragment() {
    private lateinit var binding: FragmentStoryBinding
    private lateinit var storiesAdapter: StoriesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story, container, false)
        val view = binding.root

        binding.setLifecycleOwner(this)
        binding.storyViewModel = StoryViewModel(KakaoStoryApiClient.instance, AuthApiClient.instance)
        binding.storyViewModel?.isStoryUser?.observe(this, Observer {
            if (it != null && it) {
                binding.storyViewModel?.getMyStories()
            } else {
                Log.e("StoryFragment", "This user is not story user")
            }
        })

        storiesAdapter = StoriesAdapter(listOf())
        binding.storiesList.adapter = storiesAdapter
        binding.storyViewModel?.stories?.observe(this, Observer { stories ->
            storiesAdapter.stories = stories!!
            storiesAdapter.notifyDataSetChanged()
        })

        binding.storyViewModel?.requiredScopes?.observe(this, Observer { scopes ->
            if (scopes == null) return@Observer
            Log.e("has required scopes^^", scopes.toString())
            requestStoryPermission(scopes)
        })


        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (this.isVisible and isVisibleToUser) {
            binding.storyViewModel?.isStoryUser()
        }
    }

    fun requestStoryPermission(scopes: List<String>) {
        val disposable = AuthCodeService.instance.requestAuthCode(activity!!, scopes, "individual")
                .observeOn(Schedulers.io())
                .flatMap { AuthApiClient.instance.issueAccessToken(code = it) }
                .doOnSuccess { response -> AccessTokenRepo.instance.toCache(response) }
                .subscribe(
                        { response -> binding.storyViewModel?.getMyStories() },
                        { error -> Log.e("update scope", "failed")})
    }
}

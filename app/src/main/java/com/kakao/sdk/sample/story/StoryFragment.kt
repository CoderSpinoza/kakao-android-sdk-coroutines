package com.kakao.sdk.sample.story

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.transition.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kakao.sdk.kakaostory.domain.StoryApiClient
import com.kakao.sdk.kakaostory.entity.StoryImage
import com.kakao.sdk.login.domain.AuthApiClient
import com.kakao.sdk.login.presentation.AuthCodeService
import com.kakao.sdk.sample.BaseFragment
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.ViewModelFactory
import com.kakao.sdk.sample.databinding.FragmentStoryBinding
import com.kakao.sdk.sample.friends.FriendsViewModel
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

        binding.storyViewModel = ViewModelProviders.of(this, ViewModelFactory()).get(StoryViewModel::class.java)

        binding.storyViewModel?.isStoryUser?.observe(this, Observer {
            if (it != null && it) {
                binding.storyViewModel?.getMyStories()
            } else {
                Log.e("StoryFragment", "This user is not story user")
            }
        })

        storiesAdapter = StoriesAdapter(listOf())
        binding.storiesList.adapter = storiesAdapter

        binding.storiesList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        binding.storyViewModel?.stories?.observe(this, Observer { stories ->
            storiesAdapter.stories = stories!!
            storiesAdapter.notifyDataSetChanged()
        })

        binding.storyViewModel?.requiredScopes?.observe(this, Observer { scopes ->
            if (scopes == null) return@Observer
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
                .flatMap { AuthApiClient.instance.issueAccessToken(authCode = it) }
                .subscribe(
                        { response -> binding.storyViewModel?.getMyStories() },
                        { error -> Log.e("update scope", "failed")})
    }
}

@BindingAdapter("bind:storyImage")
fun loadImage(imageView: ImageView, imageUrls: List<StoryImage>?) {
    if (imageUrls != null && imageUrls.isNotEmpty()) {
        imageView.visibility = View.VISIBLE
        Glide.with(imageView.context).load(imageUrls[0].small).into(imageView)
    } else {
        imageView.visibility = View.GONE
    }
}


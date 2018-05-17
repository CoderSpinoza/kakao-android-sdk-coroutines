package com.kakao.sdk.sample.story

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.kakaostory.entity.StoryImage
import com.kakao.sdk.login.domain.AuthApiClient
import com.kakao.sdk.login.presentation.AuthCodeService
import com.kakao.sdk.sample.*
import com.kakao.sdk.sample.databinding.FragmentStoryBinding
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 *
 */
class StoryFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentStoryBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var storyViewModel: StoryViewModel

    private val navigator = Navigator.instance

    val selectedStoryObserver = Observer<Story> {
        if (it != null) goToStoryDetail(it)
    }

    val storiesObserver = Observer<List<Story>> {
        binding.storiesList.visibility = View.VISIBLE
        binding.scopeErrorBinding.scopeErrorLayout.visibility = View.GONE
        storyAdapter.stories = it!!
        storyAdapter.notifyDataSetChanged()
    }

    val isStoryUserObserver = Observer<Boolean> {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    val scopesObserver = Observer<List<String>> {
        if (it == null) return@Observer
        binding.storiesList.visibility = View.GONE
        binding.scopeErrorBinding.scopeErrorLayout.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        storyAdapter = StoryAdapter(listOf())

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story, container, false)
        binding.setLifecycleOwner(this)

        val activity = activity as MainActivity
        storyViewModel = ViewModelProviders.of(activity, viewModelFactory)[StoryViewModel::class.java]
        binding.storyViewModel = storyViewModel
        binding.storiesList.adapter = storyAdapter
        binding.storiesList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        storyViewModel.clearSelectedStory()

        binding.addStoryButton.setOnClickListener {
            goToAddStory()
        }

        binding.scopeErrorBinding.updateScopeButton.setOnClickListener {
            val requiredScopes = storyViewModel.requiredScopes.value
            if (requiredScopes != null) {
                requestStoryPermission(requiredScopes)
            }
        }

        val disposed = storyAdapter.clickEvents.subscribe { storyViewModel.selectStory(it) }

        if (userVisibleHint) {
            binding.storyViewModel?.getMyStories()
            (parentFragment as HostFragment).title = getString(R.string.kakaostory)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        storyViewModel.isStoryUser.observe(this, isStoryUserObserver)
        storyViewModel.stories.observe(this, storiesObserver)
        storyViewModel.requiredScopes.observe(this, scopesObserver)
        storyViewModel.selectedStory.observe(this, selectedStoryObserver)
    }
    override fun onPause() {
        super.onPause()
        storyViewModel.isStoryUser.removeObserver(isStoryUserObserver)
        storyViewModel.stories.removeObserver(storiesObserver)
        storyViewModel.requiredScopes.removeObserver(scopesObserver)
        storyViewModel.selectedStory.removeObserver(selectedStoryObserver)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (!userVisibleHint && isVisibleToUser) {
            binding.storyViewModel?.getMyStories()
            if (fragmentManager!!.backStackEntryCount == 0) {
                (parentFragment as HostFragment).title = getString(R.string.kakaostory)
            }
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    fun requestStoryPermission(scopes: List<String>) {
        val disposable = AuthCodeService.instance.requestAuthCode(activity!!, scopes, "individual")
                .observeOn(Schedulers.io())
                .flatMap { AuthApiClient.instance.issueAccessToken(authCode = it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            binding.storyViewModel?.clearRequiredScopes()
                            binding.storyViewModel?.getMyStories()
                        },
                        { error -> Log.e("update scope", "failed")})
    }

    fun goToAddStory() {
        val intent = Intent(activity, AddStoryActivity::class.java)
        startActivity(intent)
    }

    fun goToStoryDetail(story: Story) {
        val activity = activity as MainActivity
        activity.goToStoryDetail(story.id)
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


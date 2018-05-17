package com.kakao.sdk.sample.friends

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kakao.sdk.kakaotalk.entity.Chat
import com.kakao.sdk.login.domain.AuthApiClient
import com.kakao.sdk.login.presentation.AuthCodeService
import com.kakao.sdk.sample.HostFragment

import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.ViewModelFactory
import com.kakao.sdk.sample.databinding.FriendsFragmentBinding
import io.reactivex.schedulers.Schedulers

class FriendsFragment : Fragment() {
    private lateinit var binding: FriendsFragmentBinding
    private lateinit var friendsAdapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.friends_fragment, container, false)
                as FriendsFragmentBinding
        binding.setLifecycleOwner(this)

        friendsAdapter = FriendsAdapter(listOf())

        binding.viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(FriendsViewModel::class.java)

        binding.friendsList.adapter = friendsAdapter
        binding.viewModel?.friends?.observe(this, Observer {
            friendsAdapter.friends = it!!
            friendsAdapter.notifyDataSetChanged()
        })

        binding.viewModel?.missingScopes?.observe(this, Observer {
            requestFriendPermission(it!!)
        })
        binding.viewModel?.friendsError?.observe(this, Observer {
            Log.e("FriendsFragment", it.toString())
        })

        if (userVisibleHint) {
            binding.viewModel?.loadFriends()
            (parentFragment as HostFragment).title = getString(R.string.friends)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
//        inflater?.inflate(R.menu.)
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser and isResumed) {
            binding.viewModel?.loadFriends()
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    fun requestFriendPermission(scopes: List<String>) {
        val disposable = AuthCodeService.instance.requestAuthCode(context!!, scopes)
                .observeOn(Schedulers.io())
                .flatMap { AuthApiClient.instance.issueAccessToken(authCode = it) }
                .subscribe({ binding.viewModel?.loadFriends() }, { Log.e("FriendsFragment", "No agree")})
    }
}

@BindingAdapter("bind:friendThumbnail")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    if (imageUrl != null && imageUrl.isNotBlank()) {
        Glide.with(imageView.context).load(imageUrl).into(imageView)
    } else {
        Glide.with(imageView.context).load(R.drawable.thumb_talk).into(imageView)
    }
}



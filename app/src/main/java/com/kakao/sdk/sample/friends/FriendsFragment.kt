package com.kakao.sdk.sample.friends

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kakao.sdk.sample.HostFragment

import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FriendsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FriendsFragment : Fragment() {

    private lateinit var binding: FriendsFragmentBinding
    val friendsViewModel: FriendsViewModel by viewModel()
    private lateinit var friendsAdapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.friends_fragment, container, false)
                as FriendsFragmentBinding
        binding.lifecycleOwner = this

        friendsAdapter = FriendsAdapter(listOf())

        binding.friendsViewModel = friendsViewModel
        binding.friendsList.adapter = friendsAdapter
        friendsViewModel.friends.observe(this, Observer {
            binding.friendsList.visibility = View.VISIBLE
            binding.scopeErrorBinding.scopeErrorLayout.visibility = View.GONE
            friendsAdapter.friends = it!!
            friendsAdapter.notifyDataSetChanged()
        })

        friendsViewModel.missingScopes.observe(this, Observer {
            if (it == null || it.isEmpty()) return@Observer
            binding.friendsList.visibility = View.GONE
            binding.scopeErrorBinding.scopeErrorLayout.visibility = View.VISIBLE
        })

        binding.scopeErrorBinding.updateScopeButton.setOnClickListener {
            val scopes = friendsViewModel.missingScopes.value
            if (scopes != null && scopes.isNotEmpty()) {
                requestFriendPermission(scopes)
            }
        }
        friendsViewModel.friendsError.observe(this, Observer {
            Log.e("FriendsFragment", it.toString())
        })

        if (userVisibleHint) {
            friendsViewModel.loadFriends()
            (parentFragment as HostFragment).title = getString(R.string.friends)
        }
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser and isResumed) {
            friendsViewModel.loadFriends()
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    @SuppressLint("CheckResult")
    fun requestFriendPermission(scopes: List<String>) {
//        AuthCodeService.instance.requestAuthCode(context!!, scopes)
//                .observeOn(Schedulers.io())
//                .flatMap { AuthApiClient.instance.issueAccessToken(authCode = it) }
//                .subscribe({ viewModel.loadFriends() }, { Log.e("FriendsFragment", "No agree")})
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

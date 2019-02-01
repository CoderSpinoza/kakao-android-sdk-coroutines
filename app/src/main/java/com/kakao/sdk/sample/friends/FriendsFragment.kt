package com.kakao.sdk.sample.friends

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kakao.sdk.auth.data.AuthApiClient
import com.kakao.sdk.auth.presentation.AuthCodeService
import com.kakao.sdk.sample.HostFragment

import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FriendsFragmentBinding
import dagger.android.support.AndroidSupportInjection
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FriendsFragment : Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FriendsFragmentBinding
    private lateinit var viewModel: FriendsViewModel
    private lateinit var friendsAdapter: FriendsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.friends_fragment, container, false)
                as FriendsFragmentBinding
        binding.setLifecycleOwner(this)

        friendsAdapter = FriendsAdapter(listOf())

        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(FriendsViewModel::class.java)
        binding.friendsViewModel = viewModel
        binding.friendsList.adapter = friendsAdapter
        viewModel.friends.observe(this, Observer {
            binding.friendsList.visibility = View.VISIBLE
            binding.scopeErrorBinding.scopeErrorLayout.visibility = View.GONE
            friendsAdapter.friends = it!!
            friendsAdapter.notifyDataSetChanged()
        })

        viewModel.missingScopes.observe(this, Observer {
            if (it == null || it.isEmpty()) return@Observer
            binding.friendsList.visibility = View.GONE
            binding.scopeErrorBinding.scopeErrorLayout.visibility = View.VISIBLE
        })

        binding.scopeErrorBinding.updateScopeButton.setOnClickListener {
            val scopes = viewModel.missingScopes.value
            if (scopes != null && scopes.isNotEmpty()) {
                requestFriendPermission(scopes)
            }
        }
        viewModel.friendsError.observe(this, Observer {
            Log.e("FriendsFragment", it.toString())
        })

        if (userVisibleHint) {
            viewModel.loadFriends()
            (parentFragment as HostFragment).title = getString(R.string.friends)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser and isResumed) {
            viewModel.loadFriends()
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    fun requestFriendPermission(scopes: List<String>) {
        AuthCodeService.instance.requestAuthCode(context!!, scopes)
                .observeOn(Schedulers.io())
                .flatMap { AuthApiClient.instance.issueAccessToken(authCode = it) }
                .subscribe({ viewModel.loadFriends() }, { Log.e("FriendsFragment", "No agree")})
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



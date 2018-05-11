package com.kakao.sdk.sample.talk

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kakao.sdk.kakaotalk.entity.Chat
import com.kakao.sdk.login.domain.AuthApiClient
import com.kakao.sdk.login.presentation.AuthCodeService
import com.kakao.sdk.sample.BaseFragment
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.ViewModelFactory
import com.kakao.sdk.sample.databinding.FragmentTalkBinding
import io.reactivex.schedulers.Schedulers

/**
 *
 */
class TalkFragment : BaseFragment() {
    private lateinit var binding: FragmentTalkBinding
    private lateinit var chatsAdapter: ChatsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)
        binding.talkViewModel = ViewModelProviders.of(this, ViewModelFactory()).get(TalkViewModel::class.java)

        chatsAdapter = ChatsAdapter(emptyList())
        binding.chatsList.adapter = chatsAdapter

        val talkViewModel = binding.talkViewModel
        talkViewModel?.chats?.observe(this, Observer { chats ->
            chatsAdapter.chats = chats!!
            chatsAdapter.notifyDataSetChanged()
        })

        talkViewModel?.missingScopes?.observe(this, Observer {
            requestChatPermission(it!!)
        })
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (this.isVisible and isVisibleToUser) {
            binding.talkViewModel?.loadChats()
        }
    }

    fun requestChatPermission(scopes: List<String>) {
        val disposable = AuthCodeService.instance.requestAuthCode(context!!, scopes)
                .observeOn(Schedulers.io())
                .flatMap { AuthApiClient.instance.issueAccessToken(authCode = it) }
                .subscribe({ binding.talkViewModel?.loadChats() }, { Log.e("TalkFragment", "No agree")})
    }
}

@BindingAdapter("bind:imageUrl")
fun loadImage(imageView: ImageView, chat: Chat) {
    val imageUrl = chat.imageUrl
    if (imageUrl != null && imageUrl.isNotBlank()) {
        Glide.with(imageView.context).load(imageUrl).into(imageView)
        return
    }
    if (chat.displayMemberImages.isNotEmpty()) {
        Glide.with(imageView.context).load(chat.displayMemberImages[0]).into(imageView)
        return
    }
    Glide.with(imageView.context).load(R.drawable.thumb_talk).into(imageView)
}


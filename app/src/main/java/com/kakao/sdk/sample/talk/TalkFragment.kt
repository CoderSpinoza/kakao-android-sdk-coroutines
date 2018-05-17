package com.kakao.sdk.sample.talk

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import com.bumptech.glide.Glide
import com.kakao.sdk.kakaotalk.entity.Chat
import com.kakao.sdk.kakaotalk.entity.ChatFilter
import com.kakao.sdk.login.domain.AuthApiClient
import com.kakao.sdk.login.presentation.AuthCodeService
import com.kakao.sdk.sample.HostFragment
import com.kakao.sdk.sample.MainActivity
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.ViewModelFactory
import com.kakao.sdk.sample.databinding.FragmentTalkBinding
import io.reactivex.schedulers.Schedulers

/**
 *
 */
class TalkFragment : Fragment(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> if (userVisibleHint) viewModel.loadChats(ChatFilter.REGULAR)
            1 -> if (userVisibleHint) viewModel.loadChats(ChatFilter.DIRECT)
            2 -> if (userVisibleHint) viewModel.loadChats(ChatFilter.MULTI)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
    private lateinit var binding: FragmentTalkBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var viewModel: TalkViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(TalkViewModel::class.java)

        chatAdapter = ChatAdapter(emptyList())
        binding.chatsList.adapter = chatAdapter

        binding.talkViewModel = viewModel
        viewModel.chats.observe(this, Observer { chats ->
            chatAdapter.chats = chats!!
            chatAdapter.notifyDataSetChanged()
        })

        viewModel.missingScopes.observe(this, Observer {
            requestChatPermission(it!!)
        })

        viewModel.selectedChat.observe(this, Observer {
            goToChatDetail(it!!)
        })
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (!userVisibleHint and isVisibleToUser) {
            (parentFragment as HostFragment).title = getString(R.string.kakaotalk)
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_talk, menu)

        val item = menu?.findItem(R.id.menu_chat_filter)
        val spinner = (item?.actionView as Spinner)
        spinner.onItemSelectedListener = this@TalkFragment
    }

    fun requestChatPermission(scopes: List<String>) {
        val disposable = AuthCodeService.instance.requestAuthCode(context!!, scopes)
                .observeOn(Schedulers.io())
                .flatMap { AuthApiClient.instance.issueAccessToken(authCode = it) }
                .subscribe({ binding.talkViewModel?.loadChats(ChatFilter.REGULAR) }, { Log.e("TalkFragment", "No agree")})
    }

    fun goToChatDetail(chat: Chat) {
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


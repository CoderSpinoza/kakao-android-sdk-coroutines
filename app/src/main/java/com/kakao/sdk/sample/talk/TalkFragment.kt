package com.kakao.sdk.sample.talk

import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Menu
import android.view.MenuInflater
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.kakao.sdk.kakaotalk.entity.Chat
import com.kakao.sdk.kakaotalk.entity.ChatFilter
import com.kakao.sdk.sample.HostFragment
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FragmentTalkBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *
 */
class TalkFragment : Fragment(), AdapterView.OnItemSelectedListener, LifecycleOwner {


    private lateinit var binding: FragmentTalkBinding
    private lateinit var chatAdapter: ChatAdapter
    val talkViewModel: TalkViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        chatAdapter = ChatAdapter(emptyList())
        binding.chatsList.adapter = chatAdapter
        binding.talkViewModel = talkViewModel

        talkViewModel.chats.observe(this, Observer { chats ->
            binding.chatsList.visibility = View.VISIBLE
            binding.scopeErrorBinding.scopeErrorLayout.visibility = View.GONE
            chatAdapter.chats = chats!!
            chatAdapter.notifyDataSetChanged()
        })

        talkViewModel.requiredScopes.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                binding.chatsList.visibility = View.GONE
                binding.scopeErrorBinding.scopeErrorLayout.visibility = View.VISIBLE
            }
        })

        talkViewModel.selectedChat.observe(this, Observer {
            goToChatDetail(it!!)
        })

        binding.scopeErrorBinding.updateScopeButton.setOnClickListener {
            val scopes = talkViewModel.requiredScopes.value
            if (scopes != null && scopes.isNotEmpty()) {
                requestChatPermission(scopes)
            }
        }
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
//        val disposable = AuthCodeService.instance.requestAuthCode(context!!, scopes)
//                .observeOn(Schedulers.io())
//                .flatMap { AuthApiClient.instance.issueAccessToken(authCode = it) }
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ binding.talkViewModel?.loadChats(ChatFilter.REGULAR) }, { Log.e("TalkFragment", "No agree")})
    }

    fun goToChatDetail(chat: Chat) {
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> if (userVisibleHint) talkViewModel.loadChats(ChatFilter.REGULAR)
            1 -> if (userVisibleHint) talkViewModel.loadChats(ChatFilter.DIRECT)
            2 -> if (userVisibleHint) talkViewModel.loadChats(ChatFilter.MULTI)
        }
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

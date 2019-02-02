package com.kakao.sdk.sample.talk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.kakaotalk.TalkApiClient
import com.kakao.sdk.kakaotalk.entity.Chat
import com.kakao.sdk.kakaotalk.entity.ChatFilter
import com.kakao.sdk.auth.exception.InvalidScopeException
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
open class TalkViewModel @Inject constructor(private val apiClient: TalkApiClient) : ViewModel() {
    val chats = MutableLiveData<List<Chat>>()
    val requiredScopes = MutableLiveData<List<String>>()
    val chatsError = MutableLiveData<Throwable>()
    val selectedChat = MutableLiveData<Chat>()

    fun onCreate() {}
    fun onPause() {}
    fun onResume() {}
    fun onDestroy() {}

    fun loadChats(filter: ChatFilter) {
        val disposable = apiClient.chatList(filter = filter)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { response -> chats.postValue(response.chatList)},
                        {
                            if (it is InvalidScopeException) {
                                requiredScopes.postValue(it.errorResponse.requiredScopes)
                            } else {
                                chatsError.postValue(it)
                            }
                        }
                )
    }

    fun selectChat(chat: Chat) {
        selectedChat.postValue(chat)
    }
}
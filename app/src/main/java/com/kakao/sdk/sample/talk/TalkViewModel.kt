package com.kakao.sdk.sample.talk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.kakaotalk.data.TalkApiClient
import com.kakao.sdk.kakaotalk.data.Chat
import com.kakao.sdk.kakaotalk.entity.ChatFilter
import com.kakao.sdk.login.exception.InvalidScopeException
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
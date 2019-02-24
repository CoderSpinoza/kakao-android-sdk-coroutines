package com.kakao.sdk.sample.talk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.kakaotalk.TalkApiClient
import com.kakao.sdk.kakaotalk.entity.Chat
import com.kakao.sdk.kakaotalk.entity.ChatFilter
import com.kakao.sdk.auth.exception.InvalidScopeException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.RuntimeException
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
        GlobalScope.launch {
            try {
                val response = apiClient.chatList(filter = filter)
                chats.postValue(response.chatList)
            } catch (e: InvalidScopeException) {
                requiredScopes.postValue(e.errorResponse.requiredScopes)
            } catch (e: RuntimeException) {
                chatsError.postValue(e)
            }
        }
    }

    fun selectChat(chat: Chat) {
        selectedChat.postValue(chat)
    }
}
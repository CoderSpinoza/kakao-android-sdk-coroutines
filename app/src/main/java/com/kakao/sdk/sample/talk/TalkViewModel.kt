package com.kakao.sdk.sample.talk

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kakao.sdk.kakaotalk.domain.TalkApiClient
import com.kakao.sdk.kakaotalk.entity.Chat
import com.kakao.sdk.login.data.InvalidScopeException

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
class TalkViewModel(private val apiClient: TalkApiClient) : ViewModel() {
    val chats = MutableLiveData<List<Chat>>()
    val missingScopes = MutableLiveData<List<String>>()
    val chatsError = MutableLiveData<Throwable>()

    fun onCreate() {}
    fun onPause() {}
    fun onResume() {}
    fun onDestroy() {}

    fun loadChats() {
        val disposable = apiClient.chatList(100)
                .subscribe(
                        { response -> chats.postValue(response.chatList)},
                        {
                            if (it is InvalidScopeException) {
                                missingScopes.postValue(it.errorResponse.requiredScopes)
                            } else {
                                chatsError.postValue(it)
                            }
                        }
                )
    }
}
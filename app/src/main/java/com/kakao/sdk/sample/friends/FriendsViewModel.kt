package com.kakao.sdk.sample.friends

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.kakao.sdk.friends.domain.FriendsApiClient
import com.kakao.sdk.friends.entity.Friend
import com.kakao.sdk.login.data.InvalidScopeException

class FriendsViewModel(private val apiClient: FriendsApiClient) : ViewModel() {

    val friends = MutableLiveData<List<Friend>>()
    val missingScopes = MutableLiveData<List<String>>()
    val friendsError = MutableLiveData<Throwable>()

    fun loadFriends() {
        val disposable = apiClient.friends(limit = 100)
                .subscribe(
                        { friends.postValue(it.friends) },
                        {
                            if (it is InvalidScopeException) {
                                missingScopes.postValue(it.errorResponse.requiredScopes)
                            } else {
                                friendsError.postValue(it)
                            }
                        }
                )
    }
}

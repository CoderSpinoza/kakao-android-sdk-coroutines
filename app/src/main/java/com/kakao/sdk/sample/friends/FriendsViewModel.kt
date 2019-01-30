package com.kakao.sdk.sample.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.friends.data.FriendsApiClient
import com.kakao.sdk.friends.data.Friend
import com.kakao.sdk.login.exception.InvalidScopeException
import javax.inject.Inject

open class FriendsViewModel @Inject constructor(private val apiClient: FriendsApiClient) : ViewModel() {

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

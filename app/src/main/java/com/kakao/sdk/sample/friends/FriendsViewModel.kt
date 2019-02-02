package com.kakao.sdk.sample.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.friends.FriendsApiClient
import com.kakao.sdk.friends.entity.Friend
import com.kakao.sdk.auth.exception.InvalidScopeException
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class FriendsViewModel @Inject constructor(private val apiClient: FriendsApiClient) : ViewModel() {

    val friends = MutableLiveData<List<Friend>>()
    val missingScopes = MutableLiveData<List<String>>()
    val friendsError = MutableLiveData<Throwable>()

    fun loadFriends() {
        val disposable = apiClient.friends(limit = 100)
                .subscribeOn(Schedulers.io())
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

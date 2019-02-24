package com.kakao.sdk.sample.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.friends.FriendsApiClient
import com.kakao.sdk.friends.entity.Friend
import com.kakao.sdk.auth.exception.InvalidScopeException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import javax.inject.Inject

open class FriendsViewModel @Inject constructor(private val apiClient: FriendsApiClient)
    : ViewModel() {

    val friends = MutableLiveData<List<Friend>>()
    val missingScopes = MutableLiveData<List<String>>()
    val friendsError = MutableLiveData<Throwable>()

    fun loadFriends() {
        @Suppress("UNUSED_VARIABLE") val deferred = GlobalScope.launch {
            try {
                val response = apiClient.friends(limit = 100, secureResource = true)
                friends.postValue(response.friends)
            } catch (e: InvalidScopeException) {
                missingScopes.postValue(e.errorResponse.requiredScopes)
            } catch (e: RuntimeException) {
                friendsError.postValue(e)
            }
        }
    }
}

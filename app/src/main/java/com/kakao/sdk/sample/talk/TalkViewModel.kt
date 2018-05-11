package com.kakao.sdk.sample.talk

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.kakao.sdk.friends.domain.FriendsApiClient
import com.kakao.sdk.friends.entity.Friend

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
class TalkViewModel(private val friendsApiClient: FriendsApiClient) : ViewModel() {
    val friends = MutableLiveData<List<Friend>>()

    fun onCreate() {}
    fun onPause() {}
    fun onResume() {}
    fun onDestroy() {}

    fun loadFriends() {
        val disposable = friendsApiClient.friends(limit = 100)
                .subscribe(
                        { response -> friends.postValue(response.friends)},
                        { error -> Log.e("error", error.toString())}
                )
    }
}
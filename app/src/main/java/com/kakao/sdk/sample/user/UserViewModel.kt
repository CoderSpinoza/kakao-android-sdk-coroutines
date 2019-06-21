package com.kakao.sdk.sample.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.entity.User
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("UNUSED_VARIABLE")
/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
open class UserViewModel(private val userApiClient: UserApiClient) : ViewModel() {
    var user = MutableLiveData<User>()

    val email = MutableLiveData<String>()
    val nickname = MutableLiveData<User>()

    val userId = MutableLiveData<Long>()
    val appId = MutableLiveData<Long>()
    val expiresIn = MutableLiveData<Long>()

    val logoutEvent = MutableLiveData<Void>()

//    fun onCreate() {}
//    fun onResume() {}
//    fun onPause() {}
//    fun onDestroy() {}

    fun loadProfile() {
        GlobalScope.launch {
            val me = userApiClient.me(true)
            user.postValue(me)
            email.postValue(me.kakaoAccount.email)
        }
    }

    fun loadTokenInfo() {
        GlobalScope.launch {
            val tokenInfo = userApiClient.accessTokenInfo()
            userId.postValue(tokenInfo.id)
            appId.postValue(tokenInfo.appId)
            expiresIn.postValue(tokenInfo.expiresInMillis)
        }
    }

    fun logout() {
        GlobalScope.launch {
            userApiClient.logout()
            logoutEvent.postValue(null)
        }
    }

    fun unlink() {
        GlobalScope.launch {
            userApiClient.unlink()
            logoutEvent.postValue(null)
        }
    }
}
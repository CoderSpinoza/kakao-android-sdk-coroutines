package com.kakao.sdk.sample.user

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kakao.sdk.login.domain.UserApiClient
import com.kakao.sdk.login.entity.user.User
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@Suppress("UNUSED_VARIABLE")
/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
class UserViewModel @Inject constructor(val userApiClient: UserApiClient) : ViewModel() {
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
        val disposable = userApiClient.me(true)
                .subscribe({
                    user.postValue(it)
                    email.postValue(it.kakaoAccount.email)
                }, {})
    }

    fun loadTokenInfo() {
        val disposable = userApiClient.accessTokenInfo()
                .subscribe { tokenInfo ->
                    userId.postValue(tokenInfo.id)
                    appId.postValue(tokenInfo.appId)
                    expiresIn.postValue(tokenInfo.expiresInMillis)
        }
    }

    fun logout() {
        val disposable = userApiClient.logout()
                .subscribe { _ ->
                    logoutEvent.postValue(null)
                }
    }

    fun unlink() {
        val disposable = userApiClient.unlink()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    logoutEvent.postValue(null)
                }
    }


}
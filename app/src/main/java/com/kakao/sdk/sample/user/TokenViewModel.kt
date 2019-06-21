package com.kakao.sdk.sample.user

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.model.AccessToken

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
class TokenViewModel(private val accessTokenRepo: AccessTokenRepo)
    : ViewModel() {
    var tokenInfo: AccessToken = accessTokenRepo.fromCache()
    val accessToken = ObservableField<String>()
    val refreshToken = ObservableField<String>()
    val atExpiresAt = ObservableField<String>()
    val rtExpiresAt = ObservableField<String>()

    fun onCreate() {}
    fun onResume() {}
    fun onPause() {}
    fun onDestroy() {}

    fun isVisible() {
        tokenInfo = accessTokenRepo.fromCache()
        accessToken.set(tokenInfo.accessToken)
        refreshToken.set(tokenInfo.refreshToken)
        atExpiresAt.set(tokenInfo.accessTokenExpiresAt.toString())
        rtExpiresAt.set(tokenInfo.refreshTokenExpiresAt.toString())
    }
}
package com.kakao.sdk.login.domain

import android.content.Context
import com.kakao.sdk.login.data.DefaultAccessTokenRepo
import com.kakao.sdk.login.entity.token.AccessToken
import com.kakao.sdk.login.entity.token.AccessTokenResponse
import com.kakao.sdk.network.ApplicationProvider
import com.kakao.sdk.network.Constants
import com.kakao.sdk.network.Utility

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
interface AccessTokenRepo {
    fun fromCache(): AccessToken
    fun toCache(accessTokenResponse: AccessTokenResponse): AccessToken
    fun clearCache()

    companion object {
        val instance = DefaultAccessTokenRepo(ApplicationProvider.application.getSharedPreferences(Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY), Context.MODE_PRIVATE))
    }
}
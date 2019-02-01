package com.kakao.sdk.login.data

import android.content.Context
import com.kakao.sdk.network.ApplicationProvider
import com.kakao.sdk.network.Constants
import com.kakao.sdk.network.Utility
import io.reactivex.Observable

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
interface AccessTokenRepo {
    fun fromCache(): AccessToken
    fun toCache(accessTokenResponse: AccessTokenResponse): AccessToken
    fun clearCache()
    fun observe(): Observable<AccessToken>

    companion object {
        val instance by lazy {
            DefaultAccessTokenRepo(
                    ApplicationProvider.application.getSharedPreferences(
                            Utility.getMetadata(ApplicationProvider.application, Constants.META_APP_KEY),
                            Context.MODE_PRIVATE
                    )
            ) as AccessTokenRepo
        }
    }
}
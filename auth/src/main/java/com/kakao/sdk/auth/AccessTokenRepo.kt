package com.kakao.sdk.auth

import com.kakao.sdk.auth.model.AccessToken
import com.kakao.sdk.auth.model.AccessTokenResponse
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
            DefaultAccessTokenRepo() as AccessTokenRepo
        }
    }
}
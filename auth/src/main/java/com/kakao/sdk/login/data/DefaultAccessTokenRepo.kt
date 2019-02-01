package com.kakao.sdk.login.data

import android.content.SharedPreferences
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*

/**
 * @author kevin.kang. Created on 2018. 3. 27..
 */
class DefaultAccessTokenRepo(val appCache: SharedPreferences) : AccessTokenRepo {
    private val tokenSubject = BehaviorSubject.create<AccessToken>()
    val tokenUpdates = tokenSubject.hide()

    init {
        tokenSubject.onNext(fromCache())
    }

    override fun observe(): Observable<AccessToken> {
        return tokenUpdates
    }

    override fun clearCache() {
        appCache.edit().remove(atKey).apply()
        appCache.edit().remove(rtKey).apply()
        appCache.edit().remove(atExpiresAtKey).apply()
        appCache.edit().remove(rtExpiresAtKey).apply()
    }

    override fun toCache(accessTokenResponse: AccessTokenResponse): AccessToken {
        appCache.edit().putString(atKey, accessTokenResponse.accessToken).apply()
        if (accessTokenResponse.refreshToken != null) {
            appCache.edit().putString(rtKey, accessTokenResponse.refreshToken).apply()
        }
        appCache.edit().putLong(atExpiresAtKey, Date().time + 1000L * accessTokenResponse.accessTokenExpiresIn).apply()
        if (accessTokenResponse.refreshTokenExpiresIn != null) {
            appCache.edit().putLong(rtExpiresAtKey, Date().time + 1000L * accessTokenResponse.refreshTokenExpiresIn).apply()
        }
        val token = fromCache()
        tokenSubject.onNext(token)
        return token
    }

    override fun fromCache(): AccessToken {
        val at = appCache.getString(atKey, null)
        val atExpiresAt = Date(appCache.getLong(atExpiresAtKey, 0))
        val rt = appCache.getString(rtKey, null)
        val rtExpiresAt = Date(appCache.getLong(rtExpiresAtKey, Long.MAX_VALUE))

        return AccessToken(at, atExpiresAt, rt, rtExpiresAt)
    }

    companion object {
        const val atKey = "com.kakao.token.AccessToken"
        const val rtKey = "com.kakao.token.RefreshToken"
        const val atExpiresAtKey = "com.kakao.token.AccessToken.ExpiresAt"
        const val rtExpiresAtKey = "com.kakao.token.RefreshToken.ExpiresAt"
    }
}
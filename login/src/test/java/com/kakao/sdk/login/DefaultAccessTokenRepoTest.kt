package com.kakao.sdk.login

import android.content.Context
import android.content.SharedPreferences
import com.kakao.sdk.login.data.DefaultAccessTokenRepo
import com.kakao.sdk.login.entity.AccessToken
import com.kakao.sdk.login.entity.AccessTokenResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowLog
import java.util.*

import org.junit.Assert.*;

/**
 * @author kevin.kang. Created on 2018. 3. 28..
 */
@RunWith(RobolectricTestRunner::class)
class DefaultAccessTokenRepoTest {
    lateinit var accessTokenRepo: DefaultAccessTokenRepo
    lateinit var observable: Observable<AccessToken>

    @Before
    fun setup() {
        ShadowLog.stream = System.out
    }

    @Test
    fun toCache() {
        accessTokenRepo = DefaultAccessTokenRepo(getEmptyPreferences())
        var response = AccessTokenResponse(accessToken = "new_test_access_token", refreshToken = "new_test_refresh_token",
                accessTokenExpiresIn = 60 * 60, refreshTokenExpiresIn = 60 * 60 * 12, tokenType = "bearer", scope = "")
        accessTokenRepo.toCache(response).subscribe{ token ->
            ShadowLog.e("updated token", token.toString())
        }
        response = AccessTokenResponse(accessToken = "new_test_access_token_2", accessTokenExpiresIn = 60 * 60 * 2,
                tokenType = "bearer", scope = "")
        accessTokenRepo.toCache(response).subscribe{ token ->
            ShadowLog.e("updated token", token.toString())
        }
//        val token = accessTokenRepo.toCache(response).subscribe()
    }

    @Test
    fun fromEmptyCache() {
        accessTokenRepo = DefaultAccessTokenRepo(getEmptyPreferences())
        observable = accessTokenRepo.getTokenObservable()
        val token = accessTokenRepo.fromCache().subscribe { token ->
            ShadowLog.e("token from empty cache", token.toString())
        }
    }

    @Test
    fun fromFullCache() {
        accessTokenRepo = DefaultAccessTokenRepo(getFullPreferences())
        observable = accessTokenRepo.getTokenObservable()
        accessTokenRepo.fromCache().subscribe { token ->
            ShadowLog.e("token from full cache", token.toString())
        }
    }

    fun getEmptyPreferences(): SharedPreferences {
        return RuntimeEnvironment.application.getSharedPreferences("test_app_key", Context.MODE_PRIVATE)
    }

    fun getFullPreferences(): SharedPreferences {
        val preferences = RuntimeEnvironment.application.getSharedPreferences("test_app_key", Context.MODE_PRIVATE)
        preferences.edit().putString(DefaultAccessTokenRepo.atKey, "test_access_token").commit()
        preferences.edit().putString(DefaultAccessTokenRepo.rtKey, "test_refresh_token").commit()
        preferences.edit().putLong(DefaultAccessTokenRepo.atExpiresAtKey, Date().time + 1000L * 60 * 60 * 12).commit()
        preferences.edit().putLong(DefaultAccessTokenRepo.rtExpiresAtKey, Date().time + 1000L * 60 * 60 * 24 * 30).commit()
        return preferences
    }
}
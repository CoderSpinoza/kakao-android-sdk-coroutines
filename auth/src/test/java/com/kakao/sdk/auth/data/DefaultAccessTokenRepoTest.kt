package com.kakao.sdk.auth.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.kakao.sdk.auth.DefaultAccessTokenRepo
import com.kakao.sdk.auth.model.AccessTokenResponse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog
import java.util.*

/**
 * @author kevin.kang. Created on 2018. 3. 28..
 */
@RunWith(RobolectricTestRunner::class)
class DefaultAccessTokenRepoTest {
    lateinit var accessTokenRepo: DefaultAccessTokenRepo

    @Before
    fun setup() {
        ShadowLog.stream = System.out
    }

    @Test
    fun toCache() {
        accessTokenRepo = DefaultAccessTokenRepo(getEmptyPreferences())
        var response = AccessTokenResponse(accessToken = "new_test_access_token", refreshToken = "new_test_refresh_token",
                accessTokenExpiresIn = 60 * 60, refreshTokenExpiresIn = 60 * 60 * 12, tokenType = "bearer")
        accessTokenRepo.toCache(response)
        response = AccessTokenResponse(accessToken = "new_test_access_token_2", accessTokenExpiresIn = 60 * 60 * 2,
                tokenType = "bearer", refreshTokenExpiresIn = 60L * 60 * 24 * 30)
        accessTokenRepo.toCache(response)
//        val token = accessTokenRepo.toCache(response).subscribe()
    }

    @Test
    fun fromEmptyCache() {
        accessTokenRepo = DefaultAccessTokenRepo(getEmptyPreferences())
    }

    @Test
    fun fromFullCache() {
        accessTokenRepo = DefaultAccessTokenRepo(getFullPreferences())
    }

    fun getEmptyPreferences(): SharedPreferences {
        return ApplicationProvider.getApplicationContext<Application>().getSharedPreferences("test_app_key", Context.MODE_PRIVATE)
    }

    fun getFullPreferences(): SharedPreferences {
        val preferences = ApplicationProvider.getApplicationContext<Application>().getSharedPreferences("test_app_key", Context.MODE_PRIVATE)
        preferences.edit().putString(DefaultAccessTokenRepo.atKey, "test_access_token").commit()
        preferences.edit().putString(DefaultAccessTokenRepo.rtKey, "test_refresh_token").commit()
        preferences.edit().putLong(DefaultAccessTokenRepo.atExpiresAtKey, Date().time + 1000L * 60 * 60 * 12).commit()
        preferences.edit().putLong(DefaultAccessTokenRepo.rtExpiresAtKey, Date().time + 1000L * 60 * 60 * 24 * 30).commit()
        return preferences
    }
}
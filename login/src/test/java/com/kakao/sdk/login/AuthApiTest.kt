package com.kakao.sdk.login

import com.kakao.sdk.login.data.ApiService
import com.kakao.sdk.login.domain.AuthApi
import org.junit.Before
import org.junit.Test
import org.robolectric.shadows.ShadowLog

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
class AuthApiTest {
    private lateinit var api: AuthApi

    @Before
    fun setup() {
        ShadowLog.stream = System.out
        api = ApiService.kauth.create(AuthApi::class.java)
    }

    @Test
    fun refreshToken() {
        api.issueAccessToken("ec09748675bf7cc01d638d8c40a79c85",
                "kakaoec09748675bf7cc01d638d8c40a79c85://oauth",
                androidKeyHash = "lMXltzn4zSwq0EhwLKAo+k0zhqI=",
                clientSecret = "50LxgHsF3Q3ayNa3nJpTTMEfBR8KkY7X",
                refreshToken = "ea3TO8YU8yO9wXsb8IhTkhrzGJwUX_VTH-K21grDFE4AAAFiZx56Fg",
                grantType = "refresh_token")
                .subscribe { response ->
                    ShadowLog.e("response", response.toString())
                }

    }
}
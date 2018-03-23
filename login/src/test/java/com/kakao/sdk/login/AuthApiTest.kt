package com.kakao.sdk.login

import com.kakao.sdk.login.data.AccessTokenRequest
import io.reactivex.Observable
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
        Observable.just(AccessTokenRequest("dd4e9cb75815cbdf7d87ed721a659baf",
                "kakaodd4e9cb75815cbdf7d87ed721a659baf://oauth",
                androidKeyHash = "lMXltzn4zSwq0EhwLKAo+k0zhqI=",
                clientSecret = "50LxgHsF3Q3ayNa3nJpTTMEfBR8KkY7X",
                refreshToken = "VOpbs755iCxiklXV-kgF8H3Lxse45UIe9ftMEQo8BRIAAAFiU581Rw",
                grantType = "refresh_token"))
                .map { req -> req.toMap() }
                .flatMap { reqMap -> api.issueAccessToken(reqMap) }
                .subscribe { response ->
                    ShadowLog.e("response", response.toString())
                }
    }
}
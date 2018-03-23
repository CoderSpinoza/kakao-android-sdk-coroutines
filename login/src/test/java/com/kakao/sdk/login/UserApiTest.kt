package com.kakao.sdk.login

import com.google.gson.Gson
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.robolectric.shadows.ShadowLog

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
class UserApiTest {
    private lateinit var api: UserApi

    @Before
    fun setup() {
        ShadowLog.stream = System.out
        api = ApiService.kapi.create(UserApi::class.java)
    }

    @Test
    fun me() {
        Observable.just(listOf("kaccount_email"))
                .map { list ->  Gson().toJson(list) }
                .flatMap { properties -> api.me(secureResource = true, properties = properties) }
                .subscribe { me ->
                    ShadowLog.e("success", me.properties.toString())
                }
    }

    @Test
    fun accessTokenInfo() {
        api.accessTokenInfo().subscribe { tokenInfo ->
            ShadowLog.e("token_info", tokenInfo.toString())
        }
    }

    @Test
    fun logout() {
        api.logout().subscribe { response ->
            ShadowLog.e("logout", response.toString())
        }
    }

    @Test
    fun unlink() {
        api.unlink().subscribe { response ->
            ShadowLog.e("unlink", response.toString())
        }
    }
}
package com.kakao.sdk.kakaolink

import com.kakao.sdk.network.ApiService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

/**
 * @author kevin.kang. Created on 2018. 3. 22..
 */
@RunWith(RobolectricTestRunner::class)
class KakaoLinkApiTest {
    private lateinit var api: KakaoLinkApi
    @Before
    fun setup() {
        ShadowLog.stream = System.out
        api = ApiService.kapi.create(KakaoLinkApi::class.java) as KakaoLinkApi
    }

    @Test
    fun validateCustom() {
        api.validateCustom("3135", mapOf(Pair("title", "프로방스 자동차 여행"),
                Pair("description", "매년 7~8월에 프로방스 발랑솔을 중심으로 라벤더가 만개한다."))).subscribe { response ->
            ShadowLog.e("validate response", response.toString())
        }
    }

    @Test
    fun validateScrap() {
        api.validateScrap("https://developers.kakao.com").subscribe { response ->
            ShadowLog.e("scrap response", response.toString())
        }
    }
}
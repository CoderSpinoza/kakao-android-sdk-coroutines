package com.kakao.sdk.kakaotalk

import com.kakao.sdk.login.ApiService

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class KakaoTalkApiClient(val api: KakaoTalkApi = ApiService.kapi.create(KakaoTalkApi::class.java)) {
}
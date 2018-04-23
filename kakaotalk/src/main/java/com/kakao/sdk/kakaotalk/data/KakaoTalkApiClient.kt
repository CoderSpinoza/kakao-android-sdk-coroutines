package com.kakao.sdk.kakaotalk.data

import com.kakao.sdk.kakaotalk.domain.KakaoTalkApi
import com.kakao.sdk.login.data.ApiService

/**
 * @author kevin.kang. Created on 2018. 3. 30..
 */
class KakaoTalkApiClient(val api: KakaoTalkApi = ApiService.kapi.create(KakaoTalkApi::class.java)) {
}
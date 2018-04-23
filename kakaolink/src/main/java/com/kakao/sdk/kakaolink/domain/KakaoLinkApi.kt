package com.kakao.sdk.kakaolink.domain

import com.kakao.sdk.kakaolink.entity.KakaoLinkResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kevin.kang. Created on 2018. 3. 21..
 */
interface KakaoLinkApi {
    @GET("v2/api/kakaolink/talk/template/validate?link_ver=4.0")
    fun validateCustom(@Query("template_id") templateId: String,
                       @Query("template_args") templateArgs: Map<String, String>): Single<KakaoLinkResponse>

    @GET("v2/api/kakaolink/talk/template/default?link_ver=4.0")
    fun validateDefault(): Single<KakaoLinkResponse>

    @GET("v2/api/kakaolink/talk/template/scrap?link_ver=4.0")
    fun validateScrap(@Query("request_url") url: String): Single<KakaoLinkResponse>

    @GET("v2/api/kakaolink/talk/template/scrap?link_ver=4.0")
    fun validateScrap(@Query("request_url") url: String,
                      @Query("template_id") templateId: String,
                      @Query("template_args") templateArgs: Map<String, String>): Single<KakaoLinkResponse>
}
package com.kakao.sdk.kakaolink

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kevin.kang. Created on 2018. 3. 21..
 */
interface KakaoLinkApi {
    @GET("v2/api/kakaolink/talk/template/validate?link_ver=4.0")
    fun validateCustom(@Query("template_id") templateId: String,
                       @Query("template_args") templateArgs: Map<String, String>): Observable<KakaoLinkResponse>

    @GET("v2/api/kakaolink/talk/template/default?link_ver=4.0")
    fun validateDefault(): Observable<KakaoLinkResponse>

    @GET("v2/api/kakaolink/talk/template/scrap?link_ver=4.0")
    fun validateScrap(@Query("request_url") url: String): Observable<KakaoLinkResponse>

    @GET("v2/api/kakaolink/talk/template/scrap?link_ver=4.0")
    fun validateScrap(@Query("request_url") url: String,
                      @Query("template_id") templateId: String,
                      @Query("template_args") templateArgs: Map<String, String>): Observable<KakaoLinkResponse>
}
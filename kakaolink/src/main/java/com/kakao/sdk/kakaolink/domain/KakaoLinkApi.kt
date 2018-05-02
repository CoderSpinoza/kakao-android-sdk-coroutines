package com.kakao.sdk.kakaolink.domain

import com.kakao.sdk.kakaolink.Constants
import com.kakao.sdk.kakaolink.entity.KakaoLinkResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kevin.kang. Created on 2018. 3. 21..
 */
interface KakaoLinkApi {
    @GET("${Constants.VALIDATE_CUSTOM_PATH}?link_ver=4.0")
    fun validateCustom(@Query(Constants.TEMPLATE_ID) templateId: String,
                       @Query(Constants.TEMPLATE_ARGS) templateArgs: Map<String, String>? = null): Single<KakaoLinkResponse>

    @GET("${Constants.VALIDATE_DEFAULT_PATH}?link_ver=4.0")
    fun validateDefault(): Single<KakaoLinkResponse>

    @GET("${Constants.VALIDATE_SCRAP_PATH}?link_ver=4.0")
    fun validateScrap(@Query(Constants.REQUEST_URL) url: String,
                      @Query(Constants.TEMPLATE_ID) templateId: String? = null,
                      @Query(Constants.TEMPLATE_ARGS) templateArgs: Map<String, String>? = null): Single<KakaoLinkResponse>
}
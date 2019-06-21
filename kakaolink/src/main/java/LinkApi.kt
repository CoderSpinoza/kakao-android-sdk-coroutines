package com.kakao.sdk.kakaolink

import com.kakao.sdk.kakaolink.entity.KakaoLinkResponse
import com.kakao.sdk.message.template.DefaultTemplate
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author kevin.kang. Created on 2018. 3. 21..
 */
interface LinkApi {
    @GET("${Constants.VALIDATE_CUSTOM_PATH}?link_ver=4.0")
    suspend fun validateCustom(
            @Query(Constants.TEMPLATE_ID) templateId: String,
            @Query(Constants.TEMPLATE_ARGS) templateArgs: Map<String, String>? = null
    ): KakaoLinkResponse

    @GET("${Constants.VALIDATE_DEFAULT_PATH}?link_ver=4.0")
    suspend fun validateDefault(
            @Query(Constants.TEMPLATE_OBJECT) templateObject: DefaultTemplate
    ): KakaoLinkResponse

    @GET("${Constants.VALIDATE_SCRAP_PATH}?link_ver=4.0")
    suspend fun validateScrap(
            @Query(Constants.REQUEST_URL) url: String,
            @Query(Constants.TEMPLATE_ID) templateId: String? = null,
            @Query(Constants.TEMPLATE_ARGS) templateArgs: Map<String, String>? = null
    ): KakaoLinkResponse
}
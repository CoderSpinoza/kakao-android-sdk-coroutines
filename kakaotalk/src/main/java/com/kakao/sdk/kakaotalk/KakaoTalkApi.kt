package com.kakao.sdk.kakaotalk

import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
interface KakaoTalkApi {
    @GET("v1/api/talk/profile")
    fun getTalkProfile(@Query("secure_resource") secureResource: Boolean): Observable<TalkProfile>

    @POST("v2/api/talk/memo/send")
    @FormUrlEncoded
    fun sendMemo(@Field("template_id") templateId: String,
                 @Field("template_args") templateArgs: String,
                 @Header("Authorization") authorization: String): Observable<Void>

    companion object {

    }
}
package com.kakao.sdk.push

import com.kakao.sdk.push.entity.PushMessage
import com.kakao.sdk.push.entity.PushToken
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
interface PushApi {
    @POST("v1/push/register?push_type=gcm")
    @FormUrlEncoded
    fun registerPushToken(@Field("device_id") deviceId: String,
                          @Field("push_token") pushToken: String): Observable<Int>

    @GET("v1/push/tokens")
    fun getPushTokens(): Observable<List<PushToken>>

    @POST("v1/push/deregister")
    @FormUrlEncoded
    fun deregisterPushToken(@Field("device_id") deviceId: String): Observable<Void>

    @POST("v1/push/send")
    @FormUrlEncoded
    fun sendPushMessage(@Field("device_id") deviceId: String,
                        @Field("push_message") pushMessage: PushMessage): Observable<Void>
}
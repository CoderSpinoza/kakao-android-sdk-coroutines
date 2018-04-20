package com.kakao.sdk.s2

import com.kakao.sdk.s2.entity.S2Response
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
interface S2Api {
    @POST("s2/publish")
    @FormUrlEncoded
    fun publishEvent(@Field("events") events: String): Observable<S2Response>

    @POST("s2/publish/adid")
    @FormUrlEncoded
    fun publishAdidEvent(@Field("events") events: String): Observable<S2Response>
}
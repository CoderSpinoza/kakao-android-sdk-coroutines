package com.kakao.sdk.s2.domain

import com.kakao.sdk.s2.entity.S2Response
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
interface S2Api {
    @POST("s2/publish")
    @FormUrlEncoded
    fun publishEvent(@Field("events") events: String): Single<S2Response>

    @POST("s2/publish/adid")
    @FormUrlEncoded
    fun publishAdidEvent(@Field("events") events: String): Single<S2Response>
}
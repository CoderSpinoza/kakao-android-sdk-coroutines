package com.kakao.sdk.s2

import com.kakao.sdk.s2.data.S2Response
import io.reactivex.Observable
import retrofit2.http.POST

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
interface S2Api {
    @POST("s2/publish")
    fun publishEvent(): Observable<S2Response>

    @POST("s2/publish/adid")
    fun publishAdidEvent(): Observable<S2Response>
}
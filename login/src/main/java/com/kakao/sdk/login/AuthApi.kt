package com.kakao.sdk.login

import com.kakao.sdk.login.data.AccessTokenResponse
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
interface AuthApi {
    @POST("/oauth/token")
    @FormUrlEncoded
    fun issueAccessToken(@FieldMap accessTokenRequest: Map<String, String>): Observable<AccessTokenResponse>
}
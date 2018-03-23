package com.kakao.sdk.login

import com.kakao.sdk.login.data.AccessTokenInfo
import com.kakao.sdk.login.data.UserIdResponse
import com.kakao.sdk.login.data.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
interface UserApi {
    @GET("v1/user/me")
    fun me(@Query("secure_resource") secureResource: Boolean = true,
           @Query("propertyKeys") properties: String? = null): Observable<User>

    @GET("v1/user/access_token_info")
    fun accessTokenInfo(): Observable<AccessTokenInfo>

    @POST("v1/user/logout")
    fun logout(): Observable<UserIdResponse>

    @POST("v1/user/unlink")
    fun unlink(): Observable<UserIdResponse>
}
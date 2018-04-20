package com.kakao.sdk.login.data

import com.kakao.sdk.login.entity.AccessTokenResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
interface AuthApi {
    @POST("/oauth/token")
    @FormUrlEncoded
    fun issueAccessToken(@Field("client_id") clientId: String,
                         @Field("redirect_uri") redirectUri: String,
                         @Field("approval_type") approvalType: String = "individual",
                         @Field("android_key_hash") androidKeyHash: String,
                         @Field("code") authCode: String? = null,
                         @Field("refresh_token") refreshToken: String? = null,
                         @Field("client_secret") clientSecret: String? = null,
                         @Field("grant_type") grantType: String = "authorization_code"
                         ): Single<AccessTokenResponse>
}
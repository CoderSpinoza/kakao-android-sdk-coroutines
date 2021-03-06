package com.kakao.sdk.auth

import com.kakao.sdk.auth.model.AccessTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
interface AuthApi {
    @POST(Constants.TOKEN_PATH)
    @FormUrlEncoded
    suspend fun issueAccessToken(
            @Field(Constants.CLIENT_ID) clientId: String,
            @Field(Constants.REDIRECT_URI) redirectUri: String?,
            @Field(Constants.APPROVAL_TYPE) approvalType: String = Constants.INDIVIDUAL,
            @Field(Constants.ANDROID_KEY_HASH) androidKeyHash: String,
            @Field(Constants.CODE) authCode: String? = null,
            @Field(Constants.REFRESH_TOKEN) refreshToken: String? = null,
            @Field(Constants.CLIENT_SECRET) clientSecret: String? = null,
            @Field(Constants.GRANT_TYPE) grantType: String = Constants.AUTHORIZATION_CODE
    ): AccessTokenResponse
}
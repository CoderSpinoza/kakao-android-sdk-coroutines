package com.kakao.sdk.user

import com.kakao.sdk.auth.Constants
import com.kakao.sdk.user.entity.AccessTokenInfo
import com.kakao.sdk.user.entity.User
import com.kakao.sdk.user.entity.UserIdResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
interface UserApi {
    @GET(Constants.V2_ME_PATH)
    fun me(@Query(Constants.SECURE_RESOURCE) secureResource: Boolean = true,
           @Query(Constants.PROPERTYKEYS) properties: String? = null): Deferred<User>

    @GET(Constants.V1_ACCESS_TOKEN_INFO_PATH)
    fun accessTokenInfo(): Deferred<AccessTokenInfo>

    @POST(Constants.V1_UPDATE_PROFILE_PATH)
    @FormUrlEncoded
    fun updateProfile(@Field(Constants.SECURE_RESOURCE) secureResource: Boolean = true,
                      @Field(Constants.PROPERTIES) properties: Map<String, String>): Deferred<UserIdResponse>

    @POST(Constants.V1_LOGOUT_PATH)
    fun logout(): Deferred<UserIdResponse>

    @POST(Constants.V1_UNLINK_PATH)
    fun unlink(): Deferred<UserIdResponse>
}
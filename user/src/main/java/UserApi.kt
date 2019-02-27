package com.kakao.sdk.user

import com.kakao.sdk.auth.Constants
import com.kakao.sdk.user.entity.AccessTokenInfo
import com.kakao.sdk.user.entity.User
import com.kakao.sdk.user.entity.UserIdResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
interface UserApi {
    @GET(Constants.V2_ME_PATH)
    suspend fun me(
        @Query(Constants.SECURE_RESOURCE) secureResource: Boolean = true,
        @Query(Constants.PROPERTYKEYS) properties: String? = null
    ): User

    @GET(Constants.V1_ACCESS_TOKEN_INFO_PATH)
    suspend fun accessTokenInfo(): AccessTokenInfo

    @POST(Constants.V1_UPDATE_PROFILE_PATH)
    @FormUrlEncoded
    suspend fun updateProfile(
        @Field(Constants.SECURE_RESOURCE) secureResource: Boolean = true,
        @Field(Constants.PROPERTIES) properties: Map<String, String>
    ): UserIdResponse

    @POST(Constants.V1_LOGOUT_PATH)
    suspend fun logout(): UserIdResponse

    @POST(Constants.V1_UNLINK_PATH)
    suspend fun unlink(): UserIdResponse
}
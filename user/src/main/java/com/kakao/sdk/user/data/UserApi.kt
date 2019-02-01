package com.kakao.sdk.user.data

import com.kakao.sdk.auth.Constants
import io.reactivex.Single
import retrofit2.http.*

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
interface UserApi {
    @GET(Constants.V2_ME_PATH)
    fun me(@Query(Constants.SECURE_RESOURCE) secureResource: Boolean = true,
           @Query(Constants.PROPERTYKEYS) properties: String? = null): Single<User>

    @GET(Constants.V1_ACCESS_TOKEN_INFO_PATH)
    fun accessTokenInfo(): Single<AccessTokenInfo>

    @POST(Constants.V1_UPDATE_PROFILE_PATH)
    @FormUrlEncoded
    fun updateProfile(@Field(Constants.SECURE_RESOURCE) secureResource: Boolean = true,
                      @Field(Constants.PROPERTIES) properties: Map<String, String>): Single<UserIdResponse>

//    @GET(Constants.V1_AGE_AUTH_INFO_PATH)
//    fun ageAuthInfo(@Query(Constants.AGE_LIMIT) ageLimit: String? = null,
//                @Query(Constants.PROPERTY_KEYS) propertyKeys: List<String>? = null): Single<AgeAuth>

    @POST(Constants.V1_LOGOUT_PATH)
    fun logout(): Single<UserIdResponse>

    @POST(Constants.V1_UNLINK_PATH)
    fun unlink(): Single<UserIdResponse>
}
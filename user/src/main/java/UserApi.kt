package com.kakao.sdk.user

import com.kakao.sdk.user.entity.*
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
    suspend fun logout()

    @POST(Constants.V1_UNLINK_PATH)
    suspend fun unlink()

    @GET(Constants.V1_SHIPPING_ADDRESSES_PATH)
    fun shippingAddresses(
            @Query(Constants.ADDRESS_ID) addressId: Long? = null,
            @Query(Constants.FROM_UPDATED_AT) fromUpdateAt: Int? = null,
            @Query(Constants.PAGE_SIZE) pageSize: Int? = null
    ): ShippingAddresses

    @GET(Constants.V1_SERVICE_TERMS_PATH)
    fun serviceTerms(): ServiceTermsResponse
}
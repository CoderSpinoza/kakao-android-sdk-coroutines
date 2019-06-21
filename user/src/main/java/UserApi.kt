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
    @GET("${Constants.V2_ME_PATH}?secure_resource=true")
    suspend fun me(@Query(Constants.PROPERTYKEYS) properties: String? = null): User

    @GET(Constants.V1_ACCESS_TOKEN_INFO_PATH)
    suspend fun accessTokenInfo(): AccessTokenInfo

    @POST("${Constants.V1_UPDATE_PROFILE_PATH}?secure_resource=true")
    @FormUrlEncoded
    suspend fun updateProfile(@Field(Constants.PROPERTIES) properties: Map<String, String>)

    @POST(Constants.V1_LOGOUT_PATH)
    suspend fun logout()

    @POST(Constants.V1_UNLINK_PATH)
    suspend fun unlink()

    @GET(Constants.V1_SHIPPING_ADDRESSES_PATH)
    suspend fun shippingAddresses(
            @Query(Constants.ADDRESS_ID) addressId: Long? = null,
            @Query(Constants.FROM_UPDATED_AT) fromUpdateAt: Int? = null,
            @Query(Constants.PAGE_SIZE) pageSize: Int? = null
    ): ShippingAddresses

    @GET(Constants.V1_SERVICE_TERMS_PATH)
    suspend fun serviceTerms(): ServiceTermsResponse
}
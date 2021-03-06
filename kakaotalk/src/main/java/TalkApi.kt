package com.kakao.sdk.kakaotalk

import com.kakao.sdk.kakaotalk.entity.FriendsResponse
import com.kakao.sdk.kakaotalk.entity.PlusFriendsResponse
import com.kakao.sdk.kakaotalk.entity.TalkProfile
import com.kakao.sdk.message.template.DefaultTemplate
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
interface TalkApi {
    @GET("${Constants.PROFILE_PATH}?secure_resource=true")
    suspend fun profile(): TalkProfile

    @POST(Constants.MEMO_PATH)
    @FormUrlEncoded
    suspend fun customMemo(
            @Field(Constants.TEMPLATE_ID) templateId: String,
            @Field(Constants.TEMPLATE_ARGS) templateArgs: Map<String, String>? = null
    )

    @POST(Constants.MEMO_DEFAULT_PATH)
    suspend fun defaultMemo(templateParams: DefaultTemplate)

    @POST(Constants.MEMO_SCRAP_PATH)
    suspend fun scrapMemo(
            @Field(Constants.REQUEST_URL) requestUrl: String,
            @Field(Constants.TEMPLATE_ID) templateId: Long? = null,
            @Field(Constants.TEMPLATE_ARGS) templateArgs: Map<String, String>? = null)

    @GET(Constants.V1_PLUS_FRIENDS_PATH)
    suspend fun plusFriends(@Query(Constants.PLUS_FRIEND_PUBLIC_IDS) publicIds: String? = null): PlusFriendsResponse

    @GET(Constants.V1_FRIENDS_PATH)
    suspend fun friends(@Query(Constants.OFFSET) offset: Int? = null, @Query(Constants.LIMIT) limit: Int? = null, @Query(Constants.ORDER) order: String? = null): FriendsResponse
}
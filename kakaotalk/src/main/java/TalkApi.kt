package com.kakao.sdk.kakaotalk

import com.kakao.sdk.kakaotalk.entity.ChatListResponse
import com.kakao.sdk.kakaotalk.entity.TalkProfile
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
interface TalkApi {
    @GET(Constants.PROFILE_PATH)
    suspend fun profile(
        @Query(Constants.SECURE_RESOURCE) secureResource: Boolean? = null
    ): TalkProfile

    @GET("${Constants.CHATS_PATH}?secure_resource=true")
    suspend fun chatList(
        @Query(Constants.FROM_ID) fromId: Int? = null,
        @Query(Constants.LIMIT) limit: Int? = null,
        @Query(Constants.ORDER) order: String? = null,
        @Query(Constants.FILTER) filter: String? = null
    ): ChatListResponse

    @POST(Constants.MEMO_PATH)
    @FormUrlEncoded
    suspend fun sendMemo(
        @Field(Constants.TEMPLATE_ID) templateId: String,
        @Field(Constants.TEMPLATE_ARGS) templateArgs: Map<String, String>? = null
    )

    @POST(Constants.MESSAGE_PATH)
    @FormUrlEncoded
    suspend fun sendMessage(
        @Field(Constants.RECEIVER_ID_TYPE) receiverIdType: String,
        @Field(Constants.RECEIVER_ID) receiverId: String,
        @Field(Constants.TEMPLATE_ID) templateId: String,
        @Field(Constants.TEMPLATE_ARGS) templateArgs: Map<String, String>? = null
    )

//    @POST("v2/api/talk/memo/scrap/send")
//    fun sendMemo(): Completable
//
//    @POST("v2/api/talk/memo/default/send")
//    fun sendMemo(@Field): Completable
//
//    @POST("v2/api/talk/message/scrap/send")
//    fun sendMessage(): Completable
//
//    @POST("v2/api/talk/message/default/send")
//    fun sendMessage(): Completable
}
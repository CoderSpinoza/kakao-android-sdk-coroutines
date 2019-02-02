package com.kakao.sdk.kakaotalk

import com.kakao.sdk.kakaotalk.entity.ChatListResponse
import com.kakao.sdk.kakaotalk.entity.TalkProfile
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
interface TalkApi {
    @GET(Constants.PROFILE_PATH)
    fun profile(@Query(Constants.SECURE_RESOURCE) secureResource: Boolean? = null): Single<TalkProfile>

    @GET(Constants.CHATS_PATH)
    fun chatList(@Query(Constants.FROM_ID) fromId: Int? = null,
                 @Query(Constants.LIMIT) limit: Int? = null,
                 @Query(Constants.ORDER) order: String? = null,
                 @Query(Constants.FILTER) filter: String? = null): Single<ChatListResponse>

    @POST(Constants.MEMO_PATH)
    @FormUrlEncoded
    fun sendMemo(
                 @Field(Constants.TEMPLATE_ID) templateId: String,
                 @Field(Constants.TEMPLATE_ARGS) templateArgs: Map<String, String>? = null): Completable

    @POST(Constants.MESSAGE_PATH)
    @FormUrlEncoded
    fun sendMessage(@Field(Constants.RECEIVER_ID_TYPE) receiverIdType: String,
                    @Field(Constants.RECEIVER_ID) receiverId: String,
                    @Field(Constants.TEMPLATE_ID) templateId: String,
                    @Field(Constants.TEMPLATE_ARGS) templateArgs: Map<String, String>? = null): Completable

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
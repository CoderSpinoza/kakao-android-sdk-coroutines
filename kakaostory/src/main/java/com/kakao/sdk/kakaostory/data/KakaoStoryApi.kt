package com.kakao.sdk.kakaostory

import android.net.Uri
import com.kakao.sdk.kakaostory.entity.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
interface KakaoStoryApi {
    @GET("v1/api/story/isstoryuser")
    fun isStoryUser(): Observable<IsStoryUserResponse>

    @GET("v1/api/story/profile")
    fun getProfile(@Query("secure_resource") secureResource: Boolean): Observable<StoryProfile>

    @GET("v1/api/story/mystory")
    fun getMyStory(@Query("id") id: String): Observable<Story>

    @GET("v1/api/story/mystories")
    fun getMyStories(): Observable<List<Story>>

    @GET("v1/api/story/mystories")
    fun getMyStories(@Query("last_id") lastId: String): Observable<List<Story>>

    @FormUrlEncoded
    @POST("v1/api/story/post/note")
    fun postNote(@Field("content") content: String,
                 @Field("permission") permission: Story.Permission,
                 @Field("enable_share") enableShare: Boolean,
                 @Field("android_exec_param") androidExecParams: Uri,
                 @Field("ios_exec_param") iosExecParams: Uri,
                 @Field("android_market_param") androidMarketParams: Uri,
                 @Field("ios_market_param") iosMarketParams: Uri): Observable<StoryPostResponse>

    @FormUrlEncoded
    @POST("v1/api/story/post/photo")
    fun postPhoto(@Query("image_url_list") images: String,
                  @Field("content") content: String,
                  @Field("permission") permission: Story.Permission,
                  @Field("enable_share") enableShare: Boolean,
                  @Field("android_exec_param") androidExecParams: Uri,
                  @Field("ios_exec_param") iosExecParams: Uri,
                  @Field("android_market_param") androidMarketParams: Uri,
                  @Field("ios_market_param") iosMarketParams: Uri): Observable<StoryPostResponse>

    @FormUrlEncoded
    @POST("v1/api/story/post/link")
    fun postLink(@Field("link_info") linkInfo: LinkInfo,
                 @Field("content") content: String,
                 @Field("permission") permission: Story.Permission,
                 @Field("enable_share") enableShare: Boolean,
                 @Field("android_exec_param") androidExecParams: Uri,
                 @Field("ios_exec_param") iosExecParams: Uri,
                 @Field("android_market_param") androidMarketParams: Uri,
                 @Field("ios_market_param") iosMarketParams: Uri): Observable<StoryPostResponse>

    @DELETE("v1/api/story/delete/mystory")
    fun deleteStory(@Query("id") id: String): Observable<Void>

    @GET("v1/api/story/linkinfo")
    fun scrapLink(@Query("url") url: String): Observable<LinkInfo>

    @Multipart
    @POST("v1/api/story/upload/multi")
    fun scrapImages(@Part images: List<MultipartBody.Part>): Observable<List<String>>
}
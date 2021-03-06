package com.kakao.sdk.kakaostory

import com.kakao.sdk.kakaostory.entity.IsStoryUserResponse
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.kakaostory.entity.StoryPostResponse
import com.kakao.sdk.kakaostory.entity.StoryProfile
import com.kakao.sdk.kakaostory.entity.LinkInfo
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.Query
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.Part

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
interface StoryApi {
    @GET(Constants.IS_STORY_USER_PATH)
    suspend fun isStoryUser(): IsStoryUserResponse

    @GET("${Constants.STORY_PROFILE_PATH}?secure_resource=true")
    suspend fun profile(): StoryProfile

    @GET(Constants.GET_STORY_PATH)
    suspend fun myStory(@Query(Constants.ID) id: String): Story

    @GET(Constants.GET_STORIES_PATH)
    suspend fun myStories(@Query(Constants.LAST_ID) lastId: String? = null): List<Story>

    @FormUrlEncoded
    @POST(Constants.POST_NOTE_PATH)
    suspend fun postNote(
            @Field(Constants.CONTENT) content: String,
            @Field(Constants.PERMISSION) permission: Story.Permission,
            @Field(Constants.ENABLE_SHARE) enableShare: Boolean,
            @Field(Constants.ANDROID_EXEC_PARAM) androidExecParams: String? = null,
            @Field(Constants.IOS_EXEC_PARAM) iosExecParams: String? = null,
            @Field(Constants.ANDROID_MARKET_PARAM) androidMarketParams: String? = null,
            @Field(Constants.IOS_MARKET_PARAM) iosMarketParams: String? = null
    ): StoryPostResponse

    @FormUrlEncoded
    @POST(Constants.POST_PHOTO_PATH)
    suspend fun postPhoto(
            @Field(Constants.IMAGE_URL_LIST) images: String,
            @Field(Constants.CONTENT) content: String,
            @Field(Constants.PERMISSION) permission: Story.Permission,
            @Field(Constants.ENABLE_SHARE) enableShare: Boolean,
            @Field(Constants.ANDROID_EXEC_PARAM) androidExecParams: String? = null,
            @Field(Constants.IOS_EXEC_PARAM) iosExecParams: String? = null,
            @Field(Constants.ANDROID_MARKET_PARAM) androidMarketParams: String? = null,
            @Field(Constants.IOS_MARKET_PARAM) iosMarketParams: String? = null
    ): StoryPostResponse

    @FormUrlEncoded
    @POST(Constants.POST_LINK_PATH)
    suspend fun postLink(
            @Field(Constants.LINK_INFO) linkInfo: LinkInfo,
            @Field(Constants.CONTENT) content: String,
            @Field(Constants.PERMISSION) permission: Story.Permission,
            @Field(Constants.ENABLE_SHARE) enableShare: Boolean,
            @Field(Constants.ANDROID_EXEC_PARAM) androidExecParams: String? = null,
            @Field(Constants.IOS_EXEC_PARAM) iosExecParams: String? = null,
            @Field(Constants.ANDROID_MARKET_PARAM) androidMarketParams: String? = null,
            @Field(Constants.IOS_MARKET_PARAM) iosMarketParams: String? = null
    ): StoryPostResponse

    @DELETE(Constants.DELETE_STORY_PATH)
    suspend fun deleteStory(@Query(Constants.ID) id: String)

    @GET(Constants.SCRAP_LINK_PATH)
    suspend fun scrapLink(@Query(Constants.URL) url: String): LinkInfo

    @Multipart
    @POST(Constants.SCRAP_IMAGES_PATH)
    suspend fun scrapImages(@Part images: List<MultipartBody.Part>): List<String>
}
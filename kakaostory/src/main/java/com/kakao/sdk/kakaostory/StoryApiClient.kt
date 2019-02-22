package com.kakao.sdk.kakaostory

import com.kakao.sdk.kakaostory.entity.*
import java.io.File

/**
 * @author kevin.kang. Created on 2018. 5. 9..
 */
interface StoryApiClient {
    suspend fun isStoryUser(): IsStoryUserResponse

    suspend fun profile(secureResource: Boolean? = null): StoryProfile

    suspend fun myStory(id: String): Story

    suspend fun myStories(lastId: String? = null): List<Story>

    suspend fun postNote(content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 androidExecParams: Map<String, String>? = null,
                 iosExecParams: Map<String, String>? = null,
                 androidMarketParams: Map<String, String>? = null,
                 iosMarketParams: Map<String, String>? = null): StoryPostResponse

    suspend fun postLink(linkInfo: LinkInfo,
                 content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 androidExecParams: Map<String, String>? = null,
                 iosExecParams: Map<String, String>? = null,
                 androidMarketParams: Map<String, String>? = null,
                 iosMarketParams: Map<String, String>? = null): StoryPostResponse

    suspend fun postPhoto(images: List<String>,
                  content: String,
                  permission: Story.Permission,
                  enableShare: Boolean,
                  androidExecParams: Map<String, String>? = null,
                  iosExecParams: Map<String, String>? = null,
                  androidMarketParams: Map<String, String>? = null,
                  iosMarketParams: Map<String, String>? = null): StoryPostResponse

    suspend fun postNote(content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 androidExecParams: String? = null,
                 iosExecParams: String? = null,
                 androidMarketParams: String? = null,
                 iosMarketParams: String? = null): StoryPostResponse

    suspend fun postPhoto(images: List<String>,
                  content: String,
                  permission: Story.Permission,
                  enableShare: Boolean,
                  androidExecParams: String? = null,
                  iosExecParams: String? = null,
                  androidMarketParams: String? = null,
                  iosMarketParams: String? = null): StoryPostResponse

    suspend fun postLink(linkInfo: LinkInfo,
                 content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 androidExecParams: String? = null,
                 iosExecParams: String? = null,
                 androidMarketParams: String? = null,
                 iosMarketParams: String? = null): StoryPostResponse

    suspend fun deleteStory(id: String): Unit

    suspend fun scrapLink(url: String): LinkInfo

    suspend fun scrapImages(images: List<File>): List<String>

    companion object {
        val instance by lazy {
            DefaultStoryApiClient() as StoryApiClient
        }
    }
}
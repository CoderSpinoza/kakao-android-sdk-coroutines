package com.kakao.sdk.kakaostory.data

import com.kakao.sdk.auth.data.ApiErrorInterceptor
import com.kakao.sdk.auth.data.ApiService
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

/**
 * @author kevin.kang. Created on 2018. 5. 9..
 */
interface StoryApiClient {
    fun isStoryUser(): Single<IsStoryUserResponse>

    fun profile(secureResource: Boolean? = null): Single<StoryProfile>

    fun myStory(id: String): Single<Story>

    fun myStories(lastId: String? = null): Single<List<Story>>

    fun postNote(content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 androidExecParams: Map<String, String>? = null,
                 iosExecParams: Map<String, String>? = null,
                 androidMarketParams: Map<String, String>? = null,
                 iosMarketParams: Map<String, String>? = null): Single<StoryPostResponse>

    fun postLink(linkInfo: LinkInfo,
                 content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 androidExecParams: Map<String, String>? = null,
                 iosExecParams: Map<String, String>? = null,
                 androidMarketParams: Map<String, String>? = null,
                 iosMarketParams: Map<String, String>? = null): Single<StoryPostResponse>

    fun postPhoto(images: List<String>,
                  content: String,
                  permission: Story.Permission,
                  enableShare: Boolean,
                  androidExecParams: Map<String, String>? = null,
                  iosExecParams: Map<String, String>? = null,
                  androidMarketParams: Map<String, String>? = null,
                  iosMarketParams: Map<String, String>? = null): Single<StoryPostResponse>

    fun postNote(content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 androidExecParams: String? = null,
                 iosExecParams: String? = null,
                 androidMarketParams: String? = null,
                 iosMarketParams: String? = null): Single<StoryPostResponse>

    fun postPhoto(images: List<String>,
                  content: String,
                  permission: Story.Permission,
                  enableShare: Boolean,
                  androidExecParams: String? = null,
                  iosExecParams: String? = null,
                  androidMarketParams: String? = null,
                  iosMarketParams: String? = null): Single<StoryPostResponse>

    fun postLink(linkInfo: LinkInfo,
                 content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 androidExecParams: String? = null,
                 iosExecParams: String? = null,
                 androidMarketParams: String? = null,
                 iosMarketParams: String? = null): Single<StoryPostResponse>

    fun deleteStory(id: String): Completable

    fun scrapLink(url: String): Single<LinkInfo>

    fun scrapImages(images: List<File>): Single<List<String>>

    companion object {
        val instance by lazy {
            DefaultStoryApiClient(
                    ApiService.kapi.create(StoryApi::class.java),
                    ApiErrorInterceptor.instance
            ) as StoryApiClient
        }
    }
}
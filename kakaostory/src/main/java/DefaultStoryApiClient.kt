package com.kakao.sdk.kakaostory

import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.common.Utility
import com.kakao.sdk.kakaostory.entity.IsStoryUserResponse
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.kakaostory.entity.StoryPostResponse
import com.kakao.sdk.kakaostory.entity.StoryProfile
import com.kakao.sdk.kakaostory.entity.LinkInfo
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

import java.io.File

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 20..
 */
class DefaultStoryApiClient(
        val api: StoryApi = OAuthApiFactory.kapi.create(StoryApi::class.java),
        private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance
) : StoryApiClient {
    override suspend fun isStoryUser(): IsStoryUserResponse {
        return apiErrorInterceptor.handleApiError {
            api.isStoryUser()
        }
    }

    override suspend fun profile(): StoryProfile {
        return apiErrorInterceptor.handleApiError { api.profile() }
    }

    override suspend fun myStory(id: String): Story {
        return apiErrorInterceptor.handleApiError {
            api.myStory(id)
        }
    }

    override suspend fun myStories(lastId: String?): List<Story> {
        return apiErrorInterceptor.handleApiError {
            api.myStories()
        }
    }

    override suspend fun postNote(
            content: String,
            permission: Story.Permission,
            enableShare: Boolean,
            androidExecParams: Map<String, String>?,
            iosExecParams: Map<String, String>?,
            androidMarketParams: Map<String, String>?,
            iosMarketParams: Map<String, String>?
    ): StoryPostResponse {
        return apiErrorInterceptor.handleApiError {
            api.postNote(content, permission, enableShare,
                    Utility.buildQuery(androidExecParams),
                    Utility.buildQuery(iosExecParams),
                    Utility.buildQuery(androidMarketParams),
                    Utility.buildQuery(iosMarketParams))
        }
    }

    override suspend fun postLink(
            linkInfo: LinkInfo,
            content: String,
            permission: Story.Permission,
            enableShare: Boolean,
            androidExecParams: Map<String, String>?,
            iosExecParams: Map<String, String>?,
            androidMarketParams: Map<String, String>?,
            iosMarketParams: Map<String, String>?
    ): StoryPostResponse {

        return apiErrorInterceptor.handleApiError {
            api.postLink(linkInfo, content, permission, enableShare,
                    Utility.buildQuery(androidExecParams),
                    Utility.buildQuery(iosExecParams),
                    Utility.buildQuery(androidMarketParams),
                    Utility.buildQuery(iosMarketParams))
        }
    }

    override suspend fun postPhoto(
            images: List<String>,
            content: String,
            permission: Story.Permission,
            enableShare: Boolean,
            androidExecParams: Map<String, String>?,
            iosExecParams: Map<String, String>?,
            androidMarketParams: Map<String, String>?,
            iosMarketParams: Map<String, String>?
    ): StoryPostResponse {
        return apiErrorInterceptor.handleApiError {
            api.postPhoto(KakaoGsonFactory.base.toJson(images), content, permission, enableShare,
                    Utility.buildQuery(androidExecParams),
                    Utility.buildQuery(iosExecParams),
                    Utility.buildQuery(androidMarketParams),
                    Utility.buildQuery(iosMarketParams))
        }
    }

    override suspend fun deleteStory(id: String) {
        return apiErrorInterceptor.handleApiError {
            api.deleteStory(id)
        }
    }

    override suspend fun scrapLink(url: String): LinkInfo {
        return apiErrorInterceptor.handleApiError {
            api.scrapLink(url)
        }
    }

    override suspend fun scrapImages(images: List<File>): List<String> {
        val result = images
                .map { Pair(it.name, RequestBody.create(MediaType.parse("image/*"), it)) }
                .mapIndexed { index, pair ->
                    MultipartBody.Part.createFormData("${Constants.FILE}_$index", pair.first, pair.second)
                }
        return apiErrorInterceptor.handleApiError {
            api.scrapImages(result)
        }
    }
}
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
            api.isStoryUser().await()
        }
    }

    override suspend fun profile(secureResource: Boolean?): StoryProfile {
        return apiErrorInterceptor.handleApiError {
            api.profile(secureResource).await()
        }
    }

    override suspend fun myStory(id: String): Story {
        return apiErrorInterceptor.handleApiError {
            api.myStory(id).await()
        }
    }

    override suspend fun myStories(lastId: String?): List<Story> {
        return apiErrorInterceptor.handleApiError {
            api.myStories().await()
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
                    Utility.buildQuery(iosMarketParams)).await()
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
                    Utility.buildQuery(iosMarketParams)).await()
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
                    Utility.buildQuery(iosMarketParams)).await()
        }
    }

    override suspend fun deleteStory(id: String) {
        return apiErrorInterceptor.handleApiError {
            api.deleteStory(id).await()
        }
    }

    override suspend fun scrapLink(url: String): LinkInfo {
        return apiErrorInterceptor.handleApiError {
            api.scrapLink(url).await()
        }
    }

    override suspend fun scrapImages(images: List<File>): List<String> {
        return emptyList()
//        return Single.just(images.map { Pair(it.name, RequestBody.create(MediaType.parse("image/*"), it)) }
//                .mapIndexed { index, pair ->  MultipartBody.Part.createFormData("${Constants.FILE}_$index", pair.first, pair.second)})
//                .flatMap { api.scrapImages(it) }
//                .compose(apiErrorInterceptor.handleApiError())
    }
}
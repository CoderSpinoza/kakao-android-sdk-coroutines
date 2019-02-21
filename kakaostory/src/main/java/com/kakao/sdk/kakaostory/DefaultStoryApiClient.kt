package com.kakao.sdk.kakaostory

import com.kakao.sdk.auth.network.ApiErrorInterceptor
import com.kakao.sdk.auth.network.OAuthApiFactory
import com.kakao.sdk.common.KakaoGsonFactory
import com.kakao.sdk.common.Utility
import com.kakao.sdk.kakaostory.entity.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 20..
 */
class DefaultStoryApiClient(val api: StoryApi = OAuthApiFactory.kapi.create(StoryApi::class.java),
                            private val apiErrorInterceptor: ApiErrorInterceptor = ApiErrorInterceptor.instance): StoryApiClient {
    override suspend fun isStoryUser(): IsStoryUserResponse {
        return api.isStoryUser().await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun profile(secureResource: Boolean?): StoryProfile {
        return api.profile(secureResource).await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun myStory(id: String): Story {
        return api.myStory(id).await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun myStories(lastId: String?): List<Story> {
        return api.myStories().await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun postNote(content: String,
                                  permission: Story.Permission,
                                  enableShare: Boolean,
                                  androidExecParams: Map<String, String>?,
                                  iosExecParams: Map<String, String>?,
                                  androidMarketParams: Map<String, String>?,
                                  iosMarketParams: Map<String, String>?): StoryPostResponse {
        return api.postNote(content, permission, enableShare,
                Utility.buildQuery(androidExecParams),
                Utility.buildQuery(iosExecParams),
                Utility.buildQuery(androidMarketParams),
                Utility.buildQuery(iosMarketParams)).await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun postLink(linkInfo: LinkInfo,
                                  content: String,
                                  permission: Story.Permission,
                                  enableShare: Boolean,
                                  androidExecParams: Map<String, String>?,
                                  iosExecParams: Map<String, String>?,
                                  androidMarketParams: Map<String, String>?,
                                  iosMarketParams: Map<String, String>?): StoryPostResponse {

        return api.postLink(linkInfo, content, permission, enableShare,
                Utility.buildQuery(androidExecParams),
                Utility.buildQuery(iosExecParams),
                Utility.buildQuery(androidMarketParams),
                Utility.buildQuery(iosMarketParams)).await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun postPhoto(images: List<String>,
                                   content: String,
                                   permission: Story.Permission,
                                   enableShare: Boolean,
                                   androidExecParams: Map<String, String>?,
                                   iosExecParams: Map<String, String>?,
                                   androidMarketParams: Map<String, String>?,
                                   iosMarketParams: Map<String, String>?): StoryPostResponse {
        return api.postPhoto(KakaoGsonFactory.base.toJson(images), content, permission, enableShare,
                Utility.buildQuery(androidExecParams),
                Utility.buildQuery(iosExecParams),
                Utility.buildQuery(androidMarketParams),
                Utility.buildQuery(iosMarketParams)).await()
//                .compose(apiErrorInterceptor.handleApiError())
    }



    override suspend fun postNote(content: String,
                                  permission: Story.Permission,
                                  enableShare: Boolean,
                                  androidExecParams: String?,
                                  iosExecParams: String?,
                                  androidMarketParams: String?,
                                  iosMarketParams: String?): StoryPostResponse {
        return api.postNote(content, permission, enableShare, androidExecParams, iosExecParams, androidMarketParams, iosMarketParams).await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun postPhoto(images: List<String>,
                                   content: String,
                                   permission: Story.Permission,
                                   enableShare: Boolean,
                                   androidExecParams: String?,
                                   iosExecParams: String?,
                                   androidMarketParams: String?,
                                   iosMarketParams: String?): StoryPostResponse {
        return api.postPhoto(KakaoGsonFactory.base.toJson(images), content, permission, enableShare, androidExecParams, iosExecParams, androidMarketParams, iosMarketParams)
                .await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun postLink(linkInfo: LinkInfo,
                                  content: String,
                                  permission: Story.Permission,
                                  enableShare: Boolean,
                                  androidExecParams: String?,
                                  iosExecParams: String?,
                                  androidMarketParams: String?,
                                  iosMarketParams: String?): StoryPostResponse {
        return api.postLink(linkInfo, content, permission, enableShare, androidExecParams, iosExecParams, androidMarketParams, iosMarketParams)
                .await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun deleteStory(id: String) {
        return api.deleteStory(id).await()
//                .compose(apiErrorInterceptor.handleCompletableError())
    }

    override suspend fun scrapLink(url: String): LinkInfo {
        return api.scrapLink(url).await()
//                .compose(apiErrorInterceptor.handleApiError())
    }

    override suspend fun scrapImages(images: List<File>): List<String> {
        return emptyList()
//        return Single.just(images.map { Pair(it.name, RequestBody.create(MediaType.parse("image/*"), it)) }
//                .mapIndexed { index, pair ->  MultipartBody.Part.createFormData("${Constants.FILE}_$index", pair.first, pair.second)})
//                .flatMap { api.scrapImages(it) }
//                .compose(apiErrorInterceptor.handleApiError())
    }
}
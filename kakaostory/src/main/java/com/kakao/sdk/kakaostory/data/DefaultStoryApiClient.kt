package com.kakao.sdk.kakaostory.data

import com.google.gson.Gson
import com.kakao.sdk.auth.data.ApiErrorInterceptor
import com.kakao.sdk.network.Utility
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * @suppress
 * @author kevin.kang. Created on 2018. 3. 20..
 */
class DefaultStoryApiClient(val api: StoryApi,
                            private val apiErrorInterceptor: ApiErrorInterceptor): StoryApiClient {
    override fun isStoryUser(): Single<IsStoryUserResponse> {
        return api.isStoryUser()
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun profile(secureResource: Boolean?): Single<StoryProfile> {
        return api.profile(secureResource)
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun myStory(id: String): Single<Story> {
        return api.myStory(id)
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun myStories(lastId: String?): Single<List<Story>> {
        return api.myStories()
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun postNote(content: String,
                          permission: Story.Permission,
                          enableShare: Boolean,
                          androidExecParams: Map<String, String>?,
                          iosExecParams: Map<String, String>?,
                          androidMarketParams: Map<String, String>?,
                          iosMarketParams: Map<String, String>?): Single<StoryPostResponse> {
        return api.postNote(content, permission, enableShare,
                Utility.buildQuery(androidExecParams),
                Utility.buildQuery(iosExecParams),
                Utility.buildQuery(androidMarketParams),
                Utility.buildQuery(iosMarketParams))
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun postLink(linkInfo: LinkInfo,
                          content: String,
                          permission: Story.Permission,
                          enableShare: Boolean,
                          androidExecParams: Map<String, String>?,
                          iosExecParams: Map<String, String>?,
                          androidMarketParams: Map<String, String>?,
                          iosMarketParams: Map<String, String>?): Single<StoryPostResponse> {

        return api.postLink(linkInfo, content, permission, enableShare,
                Utility.buildQuery(androidExecParams),
                Utility.buildQuery(iosExecParams),
                Utility.buildQuery(androidMarketParams),
                Utility.buildQuery(iosMarketParams))
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun postPhoto(images: List<String>,
                           content: String,
                           permission: Story.Permission,
                           enableShare: Boolean,
                           androidExecParams: Map<String, String>?,
                           iosExecParams: Map<String, String>?,
                           androidMarketParams: Map<String, String>?,
                           iosMarketParams: Map<String, String>?): Single<StoryPostResponse> {
        return api.postPhoto(Gson().toJson(images), content, permission, enableShare,
                Utility.buildQuery(androidExecParams),
                Utility.buildQuery(iosExecParams),
                Utility.buildQuery(androidMarketParams),
                Utility.buildQuery(iosMarketParams))
                .compose(apiErrorInterceptor.handleApiError())
    }



    override fun postNote(content: String,
                          permission: Story.Permission,
                          enableShare: Boolean,
                          androidExecParams: String?,
                          iosExecParams: String?,
                          androidMarketParams: String?,
                          iosMarketParams: String?): Single<StoryPostResponse> {
        return api.postNote(content, permission, enableShare, androidExecParams, iosExecParams, androidMarketParams, iosMarketParams)
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun postPhoto(images: List<String>,
                           content: String,
                           permission: Story.Permission,
                           enableShare: Boolean,
                           androidExecParams: String?,
                           iosExecParams: String?,
                           androidMarketParams: String?,
                           iosMarketParams: String?): Single<StoryPostResponse> {
        return api.postPhoto(Gson().toJson(images), content, permission, enableShare, androidExecParams, iosExecParams, androidMarketParams, iosMarketParams)
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun postLink(linkInfo: LinkInfo,
                          content: String,
                          permission: Story.Permission,
                          enableShare: Boolean,
                          androidExecParams: String?,
                          iosExecParams: String?,
                          androidMarketParams: String?,
                          iosMarketParams: String?): Single<StoryPostResponse> {
        return api.postLink(linkInfo, content, permission, enableShare, androidExecParams, iosExecParams, androidMarketParams, iosMarketParams)
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun deleteStory(id: String): Completable {
        return api.deleteStory(id)
                .compose(apiErrorInterceptor.handleCompletableError())
    }

    override fun scrapLink(url: String): Single<LinkInfo> {
        return api.scrapLink(url)
                .compose(apiErrorInterceptor.handleApiError())
    }

    override fun scrapImages(images: List<File>): Single<List<String>> {
        return Single.just(images.map { Pair(it.name, RequestBody.create(MediaType.parse("image/*"), it)) }
                .mapIndexed { index, pair ->  MultipartBody.Part.createFormData("${Constants.FILE}_$index", pair.first, pair.second)})
                .flatMap { api.scrapImages(it) }
                .compose(apiErrorInterceptor.handleApiError())
    }
}
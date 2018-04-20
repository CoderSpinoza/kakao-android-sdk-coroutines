package com.kakao.sdk.kakaostory.domain

import com.kakao.sdk.kakaostory.entity.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*
import java.io.File

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
interface KakaoStoryUseCases {
    fun isStoryUser(): Observable<Boolean>

    fun getProfile(secureResource: Boolean? = true): Single<StoryProfile>

    fun getMyStory(id: String): Single<Story>

    fun getMyStories(): Single<List<Story>>

    fun getMyStories(@Query("last_id") lastId: String): Single<Story>

    fun postNote(content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 linkParams: LinkParams): Single<String>

    fun postPhoto(images: List<String>,
                  content: String,
                  permission: Story.Permission,
                  enableShare: Boolean,
                  linkParams: LinkParams): Single<StoryPostResponse>

    fun postLink(@Field("link_info") linkInfo: LinkInfo,
                 content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 linkParams: LinkParams): Single<StoryPostResponse>

    fun deleteStory(id: String): Completable

    fun scrapLink(url: String): Single<LinkInfo>

    fun scrapImages(images: List<File>): Single<List<String>>
}
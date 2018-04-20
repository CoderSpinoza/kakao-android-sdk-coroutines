package com.kakao.sdk.kakaostory.domain

import com.kakao.sdk.kakaostory.entity.*
import io.reactivex.Observable
import retrofit2.http.*
import java.io.File

/**
 * @author kevin.kang. Created on 2018. 3. 24..
 */
interface KakaoStoryUseCases {
    fun isStoryUser(): Observable<Boolean>

    fun getProfile(secureResource: Boolean? = true): Observable<StoryProfile>

    fun getMyStory(id: String): Observable<Story>

    fun getMyStories(): Observable<List<Story>>

    fun getMyStories(@Query("last_id") lastId: String): Observable<Story>

    fun postNote(content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 linkParams: LinkParams): Observable<String>

    fun postPhoto(images: List<String>,
                  content: String,
                  permission: Story.Permission,
                  enableShare: Boolean,
                  linkParams: LinkParams): Observable<StoryPostResponse>

    fun postLink(@Field("link_info") linkInfo: LinkInfo,
                 content: String,
                 permission: Story.Permission,
                 enableShare: Boolean,
                 linkParams: LinkParams): Observable<StoryPostResponse>

    fun deleteStory(id: String): Observable<Void>

    fun scrapLink(url: String): Observable<LinkInfo>

    fun scrapImages(images: List<File>): Observable<List<String>>
}
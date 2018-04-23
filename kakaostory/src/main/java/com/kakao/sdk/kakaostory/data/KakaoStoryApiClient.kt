package com.kakao.sdk.kakaostory.data

import com.kakao.sdk.kakaostory.domain.KakaoStoryApi
import com.kakao.sdk.kakaostory.entity.IsStoryUserResponse
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.kakaostory.entity.StoryPostResponse
import com.kakao.sdk.login.data.ApiService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
class KakaoStoryApiClient(val api: KakaoStoryApi = ApiService.kapi.create(KakaoStoryApi::class.java)) {
    fun isStoryUser(): Single<IsStoryUserResponse> {
        return api.isStoryUser().subscribeOn(Schedulers.io())
    }

    fun getMyStories(): Single<List<Story>> {
        return api.getMyStories().subscribeOn(Schedulers.io())
    }

//    fun postNote(content: String,
//                 permission: Story.Permission,
//                 enableShare: Boolean,
//                 ): Single<StoryPostResponse> {
//        return api.postNote()
//    }

    companion object {
        val instance = KakaoStoryApiClient()
    }
}
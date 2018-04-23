package com.kakao.sdk.sample.story

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.kakao.sdk.kakaostory.data.KakaoStoryApiClient
import com.kakao.sdk.kakaostory.entity.Story

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
class StoryViewModel(private val storyApiClient: KakaoStoryApiClient) : ViewModel() {
    val isStoryUser = MutableLiveData<Boolean>()
    val stories = MutableLiveData<List<Story>>()

    fun isStoryUser() {
        val disposable = storyApiClient.isStoryUser()
                .subscribe(
                        { response -> isStoryUser.postValue(response.isStoryUser)},
                        { error -> Log.e("isStoryUser", error.toString())})
    }

    fun getMyStories() {
        val disposable = storyApiClient.getMyStories()
                .subscribe(
                        { response -> stories.postValue(response)},
                        { error -> Log.e("loadPosts", error.toString())}
                )
    }
}
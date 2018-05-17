package com.kakao.sdk.sample.story

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.kakao.sdk.kakaostory.domain.StoryApiClient
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.login.data.InvalidScopeException
import io.reactivex.Single

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
class StoryViewModel(private val storyApiClient: StoryApiClient) : ViewModel() {
    val isStoryUser = MutableLiveData<Boolean>()
    val stories = MutableLiveData<List<Story>>()
    val requiredScopes = MutableLiveData<List<String>>()
    val selectedStory = MutableLiveData<Story>()

    fun isStoryUser() {
        val disposable = storyApiClient.isStoryUser()
                .subscribe(
                        { response ->
                            isStoryUser.postValue(response.isStoryUser)
                        },
                        { error -> Log.e("isStoryUser", error.toString())})
    }

    fun getMyStories() {
        val disposable = storyApiClient.isStoryUser()
                .doOnSuccess { isStoryUser.postValue(it.isStoryUser) }
                .map { response ->
                    if (response.isStoryUser) {
                        return@map response
                    } else {
                        return@map Single.error<Boolean>(RuntimeException("Not a story user"))
                    }
                }
                .flatMap { storyApiClient.myStories() }
                .doOnError {
                    if (it is InvalidScopeException) {
                        requiredScopes.postValue(it.errorResponse.requiredScopes)
                    }
                }
                .subscribe(
                        { response ->
                            stories.postValue(response)
                        },
                        { t ->
                            Log.e("StoryViewModel", t.toString())
                        }
                )
    }

    fun getStory(storyId: String) {
        val disposable = storyApiClient.myStory(storyId)
                .subscribe({ story -> selectedStory.postValue(story) },
                        { error -> Log.e("getStory", error.toString())})
    }

    fun selectStory(story: Story) {
        selectedStory.postValue(story)
    }

    fun clearSelectedStory() {
        selectedStory.value = null
    }

    fun clearRequiredScopes() {
        requiredScopes.value = null
    }
}
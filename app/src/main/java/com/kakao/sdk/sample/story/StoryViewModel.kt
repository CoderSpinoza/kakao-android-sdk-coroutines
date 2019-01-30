package com.kakao.sdk.sample.story

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.kakaostory.data.StoryApiClient
import com.kakao.sdk.kakaostory.data.Story
import com.kakao.sdk.login.exception.InvalidScopeException
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
open class StoryViewModel @Inject constructor(private val storyApiClient: StoryApiClient) : ViewModel() {
    val isStoryUser = MutableLiveData<Boolean>()
    val stories = MutableLiveData<List<Story>>()
    val requiredScopes = MutableLiveData<List<String>>()
    val selectedStory = MutableLiveData<Story>()

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
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { response ->
                            stories.postValue(response)
                        },
                        { t ->
                            if (t is InvalidScopeException) {
                                requiredScopes.postValue(t.errorResponse.requiredScopes)
                            }
                        }
                )
    }

    fun getStory(storyId: String) {
        val disposable = storyApiClient.myStory(storyId)
                .subscribeOn(Schedulers.io())
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
package com.kakao.sdk.sample.story

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.kakao.sdk.kakaostory.domain.StoryApiClient
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.login.data.InvalidScopeException

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
class StoryViewModel(private val storyApiClient: StoryApiClient) : ViewModel() {
    val isStoryUser = MutableLiveData<Boolean>()
    val stories = MutableLiveData<List<Story>>()
    val requiredScopes = MutableLiveData<List<String>>()

    fun isStoryUser() {
        val disposable = storyApiClient.isStoryUser()
                .subscribe(
                        { response -> isStoryUser.postValue(response.isStoryUser)},
                        { error -> Log.e("isStoryUser", error.toString())})
    }

    fun getMyStories() {
        val disposable = storyApiClient.myStories()
                .subscribe(
                        { response -> stories.postValue(response)},
                        { t ->
                            if (t is InvalidScopeException) {
                                Log.e("MissingScopes", t.errorResponse.requiredScopes.toString())
                                requiredScopes.postValue(t.errorResponse.requiredScopes)
                            }
                        }
                )
    }
}
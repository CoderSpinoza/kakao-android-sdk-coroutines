package com.kakao.sdk.sample.story

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.kakaostory.StoryApiClient
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.auth.exception.InvalidScopeException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

        GlobalScope.launch {
            try {
                val response = storyApiClient.isStoryUser()
                isStoryUser.postValue(response.isStoryUser)
                if (response.isStoryUser) {
                    val storiesResponse = storyApiClient.myStories()
                    stories.postValue(storiesResponse)
                }
            } catch (e: InvalidScopeException) {
                requiredScopes.postValue(e.errorResponse.requiredScopes)
            }

        }
    }

    fun getStory(storyId: String) {
        GlobalScope.launch {
            try {
                val story = storyApiClient.myStory(storyId)
                selectedStory.postValue(story)
            } catch (e: RuntimeException) {
                Log.e("getStory", e.toString())
            }
        }
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
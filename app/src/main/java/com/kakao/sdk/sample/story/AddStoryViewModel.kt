package com.kakao.sdk.sample.story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.kakaostory.StoryApiClient
import javax.inject.Inject

/**
 * @author kevin.kang. Created on 2018. 5. 24..
 */
open class AddStoryViewModel @Inject constructor(private val storyApiClient: StoryApiClient) : ViewModel() {
    val mContent = MutableLiveData<String>()
    open val canPost = MutableLiveData<Boolean>()

    open fun setContent(content: String) {
        mContent.postValue(content)
        canPost.postValue(content.isNotBlank())
    }

    open fun post() {
    }

    open fun canPost(): MutableLiveData<Boolean> {
        return canPost
    }
}
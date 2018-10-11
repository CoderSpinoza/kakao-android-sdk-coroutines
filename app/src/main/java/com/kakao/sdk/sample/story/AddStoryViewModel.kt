package com.kakao.sdk.sample.story

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.kakao.sdk.kakaostory.domain.StoryApiClient
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
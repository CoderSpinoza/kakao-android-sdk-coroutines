package com.kakao.sdk.sample.story

import android.arch.lifecycle.MutableLiveData
import com.kakao.sdk.kakaostory.domain.StoryApiClient

/**
 * @author kevin.kang. Created on 2018. 5. 25..
 */
class TestAddStoryViewModel(storyApiClient: StoryApiClient) : AddStoryViewModel(storyApiClient) {
    override val canPost = MutableLiveData<Boolean>()
    override fun setContent(content: String) {
    }

    override fun post() {
    }

    override fun canPost(): MutableLiveData<Boolean> {
        return canPost
    }
}
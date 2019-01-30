package com.kakao.sdk.sample.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.util.Log
import com.kakao.sdk.sample.friends.FriendsViewModel
import com.kakao.sdk.sample.talk.TalkViewModel
import com.kakao.sdk.sample.user.UserViewModel
import javax.inject.Singleton

import org.mockito.Mockito.*
import javax.inject.Inject

/**
 * @author kevin.kang. Created on 2018. 5. 24..
 */
@Singleton
class TestViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    init {
        DaggerTestApplicationComponent.create().inject(this)
    }
    @Inject lateinit var friendsViewModel: FriendsViewModel
    @Inject lateinit var talkViewModel: TalkViewModel
    @Inject lateinit var storyViewModel: StoryViewModel
    @Inject lateinit var addStoryViewModel: AddStoryViewModel
    @Inject lateinit var userViewModel: UserViewModel

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.e("TestViewModelFactory", "getting $modelClass")
        when (modelClass) {
            FriendsViewModel::class.java -> return friendsViewModel as T
            TalkViewModel::class.java -> return talkViewModel as T
            StoryViewModel::class.java -> return storyViewModel as T
            AddStoryViewModel::class.java -> return addStoryViewModel as T
            UserViewModel::class.java -> return userViewModel as T
        }
        throw IllegalArgumentException("Wrong View Model")
    }
}
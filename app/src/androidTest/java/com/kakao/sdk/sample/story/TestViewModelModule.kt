package com.kakao.sdk.sample.story

import androidx.lifecycle.ViewModelProvider
import android.util.Log
import com.kakao.sdk.sample.friends.FriendsViewModel
import com.kakao.sdk.sample.talk.TalkViewModel
import com.kakao.sdk.sample.user.UserViewModel
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.*
import javax.inject.Singleton

/**
 * @author kevin.kang. Created on 2018. 5. 24..
 */
@Module
class TestViewModelModule {
    @Provides @Singleton
    fun friendsViewModel(): FriendsViewModel {
        return mock(FriendsViewModel::class.java)
    }
    @Provides @Singleton
    fun talkViewModel(): TalkViewModel {
        return mock(TalkViewModel::class.java)
    }

    @Provides @Singleton
    fun storyViewModel(): StoryViewModel {
        return mock(StoryViewModel::class.java)

    }

    @Provides @Singleton
    fun addStoryViewModel(): AddStoryViewModel {
        Log.e("TestViewModelModule: ${this}", "addStoryViewModel")
        return mock(AddStoryViewModel::class.java)

    }

    @Provides @Singleton
    fun userViewModel(): UserViewModel {
        return mock(UserViewModel::class.java)
    }

    @Provides @Singleton internal fun viewModelFactory(): ViewModelProvider.Factory {
        Log.e("TestViewModelModule: ${this}", "viewModelFactory")
        return TestViewModelFactory()
    }
}
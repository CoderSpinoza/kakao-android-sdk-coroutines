package com.kakao.sdk.sample

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kakao.sdk.friends.domain.FriendsApiClient
import com.kakao.sdk.kakaostory.domain.StoryApiClient
import com.kakao.sdk.kakaotalk.domain.TalkApiClient
import com.kakao.sdk.login.domain.AccessTokenRepo
import com.kakao.sdk.login.domain.UserApiClient
import com.kakao.sdk.sample.friends.FriendsViewModel
import com.kakao.sdk.sample.story.StoryViewModel
import com.kakao.sdk.sample.talk.TalkViewModel
import com.kakao.sdk.sample.user.TokenViewModel
import com.kakao.sdk.sample.user.UserViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * @author kevin.kang. Created on 2018. 5. 11..
 */
@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return viewModels[modelClass]?.get() as T
        } catch (e: Throwable) {
            throw IllegalArgumentException("Wrong ViewModel")
        }
    }

}
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

/**
 * @author kevin.kang. Created on 2018. 5. 11..
 */
class ViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            FriendsViewModel::class.java -> return FriendsViewModel(FriendsApiClient.instance) as T
            TalkViewModel::class.java -> return TalkViewModel(TalkApiClient.instance) as T
            StoryViewModel::class.java -> return StoryViewModel(StoryApiClient.instance) as T
            UserViewModel::class.java -> return UserViewModel(UserApiClient.instance) as T
            TokenViewModel::class.java -> return TokenViewModel(AccessTokenRepo.instance) as T
        }
        throw IllegalArgumentException("Wrong ViewModel")
    }
}
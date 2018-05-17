package com.kakao.sdk.sample

import com.kakao.sdk.friends.domain.FriendsApiClient
import com.kakao.sdk.kakaostory.domain.StoryApiClient
import com.kakao.sdk.kakaotalk.domain.TalkApiClient
import com.kakao.sdk.login.domain.AccessTokenRepo
import com.kakao.sdk.login.domain.UserApiClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author kevin.kang. Created on 2018. 5. 17..
 */
@Module
class ApiClientModule {
    @Provides @Singleton
    fun talkApiClient(): TalkApiClient {
        return TalkApiClient.instance
    }
    @Provides @Singleton
    fun storyApiClient(): StoryApiClient {
        return StoryApiClient.instance
    }

    @Provides @Singleton
    fun friendsApiClient(): FriendsApiClient {
        return FriendsApiClient.instance
    }

    @Provides @Singleton
    fun userApiClient(): UserApiClient {
        return UserApiClient.instance
    }

    @Provides @Singleton
    fun accessTokenRepo(): AccessTokenRepo {
        return AccessTokenRepo.instance
    }
}
package com.kakao.sdk.sample

import android.util.Log
import com.kakao.sdk.friends.data.FriendsApiClient
import com.kakao.sdk.kakaostory.data.StoryApiClient
import com.kakao.sdk.kakaotalk.data.TalkApiClient
import com.kakao.sdk.login.data.AccessTokenRepo
import com.kakao.sdk.user.data.UserApiClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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
        return UserApiClient.withClient(OkHttpClient.Builder().addInterceptor {
            Log.e("UserApiClient", "custom log")
            return@addInterceptor it.proceed(it.request())
        })
    }

    @Provides @Singleton
    fun accessTokenRepo(): AccessTokenRepo {
        return AccessTokenRepo.instance
    }
}
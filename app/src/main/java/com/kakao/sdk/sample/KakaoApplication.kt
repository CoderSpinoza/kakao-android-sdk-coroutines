package com.kakao.sdk.sample

import android.app.Application
import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.common.ApplicationContextInfo
import com.kakao.sdk.common.KakaoSdkProvider
import com.kakao.sdk.friends.FriendsApiClient
import com.kakao.sdk.kakaostory.StoryApiClient
import com.kakao.sdk.kakaotalk.TalkApiClient
import com.kakao.sdk.partner.KakaoPhase
import com.kakao.sdk.partner.PhasedServerHosts
import com.kakao.sdk.sample.friends.FriendsViewModel
import com.kakao.sdk.sample.story.StoryViewModel
import com.kakao.sdk.sample.talk.TalkViewModel
import com.kakao.sdk.sample.user.TokenViewModel
import com.kakao.sdk.sample.user.UserViewModel
import com.kakao.sdk.user.UserApiClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * @author kevin.kang. Created on 2018. 3. 27..
 */
@Suppress("Unused")
class KakaoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val apiClientModule = module {
            single { UserApiClient.instance }
            single { TalkApiClient.instance }
            single { StoryApiClient.instance }
            single { FriendsApiClient.instance }
        }

        val extraModule = module {
            single { AccessTokenRepo.instance }
        }

        val viewModelModule = module {
            viewModel { UserViewModel(get()) }
            viewModel { FriendsViewModel(get()) }
            viewModel { TalkViewModel(get()) }
            viewModel { StoryViewModel(get()) }
            viewModel { TokenViewModel(get()) }
        }
        startKoin {
            androidLogger()
            androidContext(this@KakaoApplication)
            modules(listOf(apiClientModule, viewModelModule, extraModule))
        }
        KakaoSdkProvider.applicationContextInfo =
                ApplicationContextInfo(
                        context = this,
                        clientId = "dd4e9cb75815cbdf7d87ed721a659baf")
        KakaoSdkProvider.serverHosts = PhasedServerHosts(KakaoPhase.PRODUCTION)
    }
}

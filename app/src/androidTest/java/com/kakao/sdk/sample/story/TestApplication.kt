package com.kakao.sdk.sample.story

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.kakao.sdk.common.ApplicationContextInfo
import com.kakao.sdk.common.KakaoSdkProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * @author kevin.kang. Created on 2018. 5. 24..
 */
class TestApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {
    @Inject lateinit var dispatchingSupportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingSupportFragmentInjector

    lateinit var component: TestApplicationComponent
    fun component(): TestApplicationComponent {
        return component
    }
    override fun onCreate() {
        super.onCreate()
        KakaoSdkProvider.applicationContextInfo =
                ApplicationContextInfo(context = this, clientId = "dd4e9cb75815cbdf7d87ed721a659baf",
                        clientSecret = "50LxgHsF3Q3ayNa3nJpTTMEfBR8KkY7X")
        component = DaggerTestApplicationComponent.create()
        component.inject(this)
    }
}
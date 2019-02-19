package com.kakao.sdk.sample

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.kakao.sdk.common.ApplicationProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * @author kevin.kang. Created on 2018. 3. 27..
 */
class KakaoApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject lateinit var dispatchingSupportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingSupportFragmentInjector

    override fun onCreate() {
        super.onCreate()
        ApplicationProvider.application = this
        DaggerApplicationComponent.create().inject(this)
    }
}

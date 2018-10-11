package com.kakao.sdk.sample.story

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import android.util.Log
import com.kakao.sdk.network.ApplicationProvider
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
        ApplicationProvider.application = this
        component = DaggerTestApplicationComponent.create()
        component.inject(this)
    }
}
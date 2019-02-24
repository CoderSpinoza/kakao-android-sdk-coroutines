package com.kakao.sdk.sample.story

import com.kakao.sdk.sample.* // ktlint-disable no-wildcard-imports
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author kevin.kang. Created on 2018. 5. 24..
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class,
    ApplicationModule::class, TestViewModelModule::class, ApiClientModule::class])
interface TestApplicationComponent : AndroidInjector<TestApplication> {
    fun inject(test: AddStoryActivityTest)
    fun inject(viewModelFactory: TestViewModelFactory)
}
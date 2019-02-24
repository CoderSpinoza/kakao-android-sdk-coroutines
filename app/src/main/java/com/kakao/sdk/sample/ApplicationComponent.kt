package com.kakao.sdk.sample

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * @author kevin.kang. Created on 2018. 5. 17..
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class,
    ViewModelModule::class, ApiClientModule::class])
interface ApplicationComponent : AndroidInjector<KakaoApplication>
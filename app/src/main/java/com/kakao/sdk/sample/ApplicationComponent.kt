package com.kakao.sdk.sample

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author kevin.kang. Created on 2018. 5. 17..
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class, ApplicationModule::class, ViewModelModule::class, ApiClientModule::class])
interface ApplicationComponent: AndroidInjector<KakaoApplication>
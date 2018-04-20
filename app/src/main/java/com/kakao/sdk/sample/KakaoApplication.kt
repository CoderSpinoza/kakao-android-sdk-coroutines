package com.kakao.sdk.sample

import android.app.Application
import com.kakao.sdk.network.ApplicationProvider

/**
 * @author kevin.kang. Created on 2018. 3. 27..
 */
class KakaoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationProvider.application = this

    }
}

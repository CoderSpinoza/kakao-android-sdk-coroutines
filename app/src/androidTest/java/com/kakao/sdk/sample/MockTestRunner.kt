package com.kakao.sdk.sample

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import android.util.Log
import com.kakao.sdk.sample.story.TestApplication

/**
 * @author kevin.kang. Created on 2018. 5. 24..
 */
class MockTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}
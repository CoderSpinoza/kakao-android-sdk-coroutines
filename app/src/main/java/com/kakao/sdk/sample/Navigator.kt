package com.kakao.sdk.sample

import android.content.Context
import android.content.Intent
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.sample.login.LoginActivity

/**
 * @author kevin.kang. Created on 2018. 5. 14..
 */
class Navigator {
    fun navigateToStoryDetail(context: Context, story: Story) {
    }

    fun navigateToAddStory(context: Context) {
    }

    fun redirectToLogin(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }

    companion object {
        val instance = Navigator()
    }
}
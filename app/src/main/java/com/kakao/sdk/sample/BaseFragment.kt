package com.kakao.sdk.sample

import android.content.Intent
import android.support.v4.app.Fragment
import com.kakao.sdk.sample.login.LoginActivity

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
open class BaseFragment : Fragment() {
    fun redirectToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}
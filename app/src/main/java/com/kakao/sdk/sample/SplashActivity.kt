package com.kakao.sdk.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.sample.login.LoginActivity
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val token = AccessTokenRepo.instance.observe().value
        if (token.refreshToken == null) {
            goToLogin()
            return
        }
        GlobalScope.launch {
            try {
                val tokenInfo = UserApiClient.instance.accessTokenInfo()
                goToMain()
            } catch (e: Throwable) {
                Log.e("login error", e.toString())
                goToLogin()
            }
        }
    }

    fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}

package com.kakao.sdk.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.login.domain.AccessTokenRepo
import com.kakao.sdk.sample.login.LoginActivity
import com.kakao.sdk.user.data.UserApiClient
import io.reactivex.android.schedulers.AndroidSchedulers

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val token = AccessTokenRepo.instance.observe().blockingFirst()
        Log.e("Token at startup", token.toString())

        if (token.refreshToken == null) {
            goToLogin()
            return
        }
        val disposed = UserApiClient.instance.accessTokenInfo().observeOn(AndroidSchedulers.mainThread())
                .subscribe({ goToMain() },{ goToLogin() })
    }

    fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}

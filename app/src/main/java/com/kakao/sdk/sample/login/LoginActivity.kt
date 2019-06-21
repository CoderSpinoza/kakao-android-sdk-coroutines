package com.kakao.sdk.sample.login

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.AccessTokenRepo
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.AuthCodeService
import com.kakao.sdk.sample.MainActivity
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.ActivityLoginBinding
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.kakaoLoginButton.setOnClickListener {
            GlobalScope.launch {
                AuthCodeService.instance.requestAuthCode(this@LoginActivity)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val code = intent?.data?.getQueryParameter("code") ?: return

        GlobalScope.launch(Dispatchers.Main) {
            val token = GlobalScope.async {
                val response = AuthApiClient.instance.issueAccessToken(authCode = code)
                AccessTokenRepo.instance.toCache(response)
            }.await()
            val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(mainIntent)
        }
    }
}

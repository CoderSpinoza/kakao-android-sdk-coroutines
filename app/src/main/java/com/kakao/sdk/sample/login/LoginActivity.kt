package com.kakao.sdk.sample.login

import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.data.AuthApiClient
import com.kakao.sdk.auth.presentation.AuthCodeService
import com.kakao.sdk.sample.MainActivity
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.ActivityLoginBinding
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.kakaoLoginButton.setOnClickListener {
            AuthCodeService.instance.requestAuthCode(this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val code = intent?.data?.getQueryParameter("code") ?: return

        AuthApiClient.instance.issueAccessToken(authCode = code)
                .subscribeOn(Schedulers.io())
                .subscribe { _ ->
                    val mainIntent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(mainIntent)
                }
    }
}

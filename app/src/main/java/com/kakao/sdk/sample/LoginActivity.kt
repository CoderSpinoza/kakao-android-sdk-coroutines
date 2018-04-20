package com.kakao.sdk.sample

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kakao.sdk.login.AuthCodeService
import com.kakao.sdk.login.data.AuthApiClient
import com.kakao.sdk.login.domain.AccessTokenRepo
import com.kakao.sdk.network.StringSet
import com.kakao.sdk.network.Utility
import com.kakao.sdk.sample.databinding.ActivityLoginBinding
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
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

        val disposable = AuthApiClient.instance.issueAccessToken(code = code, redirectUri = String.format("kakao%s://oauth", Utility.getMetadata(this, StringSet.META_APP_KEY)))
                .doOnSuccess { response -> AccessTokenRepo.instance.toCache(response) }
                .subscribeOn(Schedulers.io())
                .subscribe { _ ->
                    val mainIntent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(mainIntent)
                }
    }
}

package com.kakao.sdk.sample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kakao.sdk.friends.FriendsApiClient
import com.kakao.sdk.login.AuthCodeService
import com.kakao.sdk.login.data.AuthApiClient
import com.kakao.sdk.login.domain.AccessTokenRepo
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("Token on MainActivity", AccessTokenRepo.instance.fromCache().toString())
        FriendsApiClient.instance.getFriends()
                .retryWhen { AuthApiClient.instance.refreshTokenObservable(it) }
                .retryWhen { AuthCodeService.instance.updateScopesObservable(this@MainActivity, it) }
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { response -> Log.e("friends response", "" + response.friends.size) },
                        { Log.e("error", "" + it.message + " " + it.toString()) }
                )
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}

package com.kakao.sdk.kakaotalk

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.*

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
@RunWith(RobolectricTestRunner::class)
class KakaoTalkApiTest {
    @Test
    fun simple() {
        val retrofit = Retrofit.Builder().baseUrl("https://kapi.kakao.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val api = retrofit.create(KakaoTalkApi::class.java)

        val args = HashMap<String, String>()
        args.put("username", "Hara Kang")
        args.put("labelMsg", "This is Hara Kang's text message")
        val observable = api.sendMemo("3356", JSONObject(args).toString(), "Bearer F5rwzXAqnA_RqExTapN89-xmmRpFCKynRmugYgopdaYAAAFiQ309gA")

        observable.subscribe()
    }

    @Test
    fun complex() {
        assert(false)
    }
}
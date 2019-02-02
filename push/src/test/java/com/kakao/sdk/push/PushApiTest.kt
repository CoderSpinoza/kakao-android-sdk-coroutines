package com.kakao.sdk.push

import com.kakao.sdk.network.data.KakaoConverterFactory
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.robolectric.shadows.ShadowLog
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
class PushApiTest {
    private lateinit var api: PushApi
    private lateinit var retrofit: Retrofit
    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()
        ShadowLog.stream = System.out
        val client = OkHttpClient.Builder().build()
        retrofit = Retrofit.Builder().baseUrl(server.url("/"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(KakaoConverterFactory())
                .client(client)
                .build()
        api = retrofit.create(PushApi::class.java)
    }

    @Test
    fun getPushTokens() {
//        api.getPushTokens().subscribe { tokens ->
//        }
    }

    @Test
    fun deregisterPushToken() {
//        api.deregisterPushToken("6b307f8a-4105-3e3c-aec1-dac013985c4d")
//                .subscribe {
//                }
    }

    @Test
    fun registerPushToken() {
//        api.registerPushToken("6b307f8a-4105-3e3c-aec1-dac013985c4d",
//                "dNeoya9iqkI:APA91bEy_Q_gUl4-tRG7n3VxYjpxfMeLu3HegIfcpKtg2IfXU645pSFq5HY0OMHgkSLl0PbmujSJMp2tLm0tf-hLMigxgYLJAB7Nw3MwIdfG3CqyisMEsiTdN-WTs91PGi7uEgbJiB4-")
//                .subscribe { response ->
//                }
    }

    @Test
    fun sendPushMessage() {
//        Observable.just(mapOf(Pair("content", "테스트 메시지"), Pair("friend_id", "1"), Pair("noti", "test")))
//                .map { customField -> FcmMessage(customField = customField) }
//                .map { fcmMessage -> PushMessage(fcmMessage = fcmMessage) }
//                .flatMap { pushMessage ->
//                    return@flatMap  api.sendPushMessage("6b307f8a-4105-3e3c-aec1-dac013985c4d", pushMessage).toObservable<Void>() }
//                .subscribe {
//                }
    }


}
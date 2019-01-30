package com.kakao.sdk.push

import com.kakao.sdk.login.data.ApiService
import com.kakao.sdk.push.data.FcmMessage
import com.kakao.sdk.push.data.PushApi
import com.kakao.sdk.push.data.PushMessage
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.robolectric.shadows.ShadowLog

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
class PushApiTest {
    private lateinit var api: PushApi
    @Before
    fun setup() {
        ShadowLog.stream = System.out
        api = ApiService.kapi.create(PushApi::class.java)
    }

    @Test
    fun getPushTokens() {
        api.getPushTokens().subscribe { tokens ->
            ShadowLog.e("tokens", tokens.toString())
        }
    }

    @Test
    fun deregisterPushToken() {
        api.deregisterPushToken("6b307f8a-4105-3e3c-aec1-dac013985c4d")
                .subscribe {
                    ShadowLog.e("void", "void")
                }
    }

    @Test
    fun registerPushToken() {
        api.registerPushToken("6b307f8a-4105-3e3c-aec1-dac013985c4d",
                "dNeoya9iqkI:APA91bEy_Q_gUl4-tRG7n3VxYjpxfMeLu3HegIfcpKtg2IfXU645pSFq5HY0OMHgkSLl0PbmujSJMp2tLm0tf-hLMigxgYLJAB7Nw3MwIdfG3CqyisMEsiTdN-WTs91PGi7uEgbJiB4-")
                .subscribe { response ->
                    ShadowLog.e("expires at", response.toString())
                }
    }

    @Test
    fun sendPushMessage() {
        Observable.just(mapOf(Pair("content", "테스트 메시지"), Pair("friend_id", "1"), Pair("noti", "test")))
                .map { customField -> FcmMessage(customField = customField) }
                .map { fcmMessage -> PushMessage(fcmMessage = fcmMessage) }
                .flatMap { pushMessage ->
                    ShadowLog.e("pushMessage", pushMessage.toString())
                    return@flatMap  api.sendPushMessage("6b307f8a-4105-3e3c-aec1-dac013985c4d", pushMessage).toObservable<Void>() }
                .subscribe {
                    ShadowLog.e("void", "void")
                }
    }


}
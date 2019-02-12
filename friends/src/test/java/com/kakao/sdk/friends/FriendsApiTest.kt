package com.kakao.sdk.friends

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.friends.entity.FriendsResponse
import com.kakao.sdk.network.data.ApiFactory
import com.kakao.sdk.network.data.KakaoConverterFactory
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author kevin.kang. Created on 2018. 3. 23..
 */
class FriendsApiTest {
    private lateinit var api: FriendsApi
    private lateinit var retrofit: Retrofit
    private lateinit var server: MockWebServer

    @BeforeEach fun setup() {
        server = MockWebServer()
        server.start()
        val client = OkHttpClient.Builder().build()
        retrofit = ApiFactory.withClient(server.url("/").toString(), OkHttpClient.Builder())
        api = retrofit.create(FriendsApi::class.java)
    }

    @Nested
    @DisplayName("/v1/friends")
    inner class GetFriends {
        @Test fun success() {
            val uri = javaClass.classLoader.getResource("json/friends/friends1.json")
            val file = File(uri.path)
            val body = String(file.readBytes())

            val expected = Gson().fromJson(body, JsonObject::class.java)
            val response = MockResponse().setResponseCode(200).setBody(body)
            server.enqueue(response)
            val observer = TestObserver<FriendsResponse>()

            api.friends().subscribe(observer)
            observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
            observer.assertNoErrors()
            observer.assertValueCount(1)

            observer.assertValue {
                expected["total_count"].asInt == it.totalCount
                        && expected["elements"].asJsonArray.size() == it.friends.size
                        && expected["result_id"].asString == it.resultId
                        && it.beforeUrl == null
                        && expected["after_url"].asString == it.afterUrl
            }
        }
    }

//    @Nested
//    @DisplayName("/v1/friends/operation")
//    inner class FriendsOperation {
//        @Test fun success() {
//            val uri = javaClass.classLoader.getResource("json/friends/friends1.json")
//            val file = File(uri.path)
//            val body = String(file.readBytes())
//            val expected = Gson().fromJson(body, JsonObject::class.java)
//            val response = MockResponse().setResponseCode(200).setBody(body)
//            server.enqueue(response)
//            val observer = TestObserver<FriendsResponse>()
//
//            api.friendsOperation("first_id", "second_id", FriendsOperator.INTERSECTION)
//                    .subscribe(observer)
//
//            observer.awaitTerminalEvent(1, TimeUnit.SECONDS)
//            observer.assertNoErrors()
//            observer.assertValueCount(1)
//            observer.assertValue {
//                expected["total_count"].asInt == it.totalCount
//                        && expected["elements"].asJsonArray.size() == it.friends.size
//                        && expected["result_id"].asString == it.resultId
//                        && it.beforeUrl == null
//                        && expected["after_url"].asString == it.afterUrl
//            }
//
//
//        }
//
//        @Test fun error() {
//            val uri = javaClass.classLoader.getResource("json/friends/invalid_result_id.json")
//            val file = File(uri.path)
//            val body = String(file.readBytes())
//            val expected = Gson().fromJson(body, JsonObject::class.java)
//            val response = MockResponse().setResponseCode(400).setBody(body)
//            server.enqueue(response)
//
//            val observer = TestObserver<FriendsResponse>()
//            api.friendsOperation("first_id", "second_id", FriendsOperator.INTERSECTION)
//                    .subscribe(observer)
//
//            observer.assertError {
//                it is HttpException && it.code() == 400
//            }
//        }
//    }
}
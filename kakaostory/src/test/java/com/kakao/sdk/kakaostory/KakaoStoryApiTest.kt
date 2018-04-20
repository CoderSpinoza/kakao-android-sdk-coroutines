package com.kakao.sdk.kakaostory

import android.net.Uri
import com.google.gson.Gson
import com.kakao.sdk.kakaostory.data.KakaoStoryApi
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.kakaostory.entity.StoryPostResponse
import com.kakao.sdk.login.data.AccessTokenInterceptor
import com.kakao.sdk.network.KakaoAgentInterceptor
import com.kakao.sdk.network.KakaoConverterFactory
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import okhttp3.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowLog
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
@RunWith(RobolectricTestRunner::class)
class KakaoStoryApiTest {
    private lateinit var api: KakaoStoryApi
    @Before
    fun setup() {
        ShadowLog.stream = System.out
        val retrofit = Retrofit.Builder().baseUrl("https://kapi.kakao.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(KakaoConverterFactory())
                .client(object : OkHttpClient() {
                    override fun interceptors(): MutableList<Interceptor> {
                        return mutableListOf(AccessTokenInterceptor(Observable.just("test_access_token")), KakaoAgentInterceptor())
                    }
                })
                .build()
        api = retrofit.create(KakaoStoryApi::class.java)
    }

    @Test
    fun isStoryUser() {
        val observable = api.isStoryUser().subscribe { response ->
            ShadowLog.e("isUser", "" + response.isStoryUser)
        }
    }
    @Test
    fun getProfile() {
        val observable = api.getProfile(true).subscribe { profile ->
            ShadowLog.e("profile", profile.toString())
        }
    }

    @Test
    fun getMyStories() {
        api.getMyStories()
                .doOnNext { onNext -> ShadowLog.e("stories", onNext.toString()) }
                .flatMap { stories -> stories.toObservable() }
                .firstOrError()
                .flatMap { story -> story.let { api.getMyStory(story.id).singleOrError() }}
                .subscribe { story ->
                    ShadowLog.e("single story", story.toString())
                }
    }

    @Test
    fun postNote() {
        val uri = Uri.Builder().appendQueryParameter("key1", "value1").build()
        val observable = api.postNote("content", Story.Permission.PUBLIC, true,
                uri, uri, uri, uri)
                .map { response -> response.id }
                .flatMap { storyId -> api.deleteStory(storyId) } as Observable<Void>

        observable.subscribe({ response ->
            ShadowLog.e("result", "delete")
        }, { error ->
            ShadowLog.e("error", error.toString())
        })
    }

    @Test
    fun postLink() {
        val url = "https://www.daum.net"
        val uri = Uri.Builder().appendQueryParameter("key1", "value1").build()
        val observable = api.scrapLink(url).flatMap { linkInfo ->
            ShadowLog.e("linkInfo", linkInfo.toString())
            return@flatMap api.postLink(linkInfo, "content", Story.Permission.PUBLIC, true, uri, uri, uri, uri)
        } as Observable<StoryPostResponse>

        observable.subscribe { response ->
            ShadowLog.e("result", response.toString())
        }
    }

    @Test
    fun postPhoto() {
        val uri = Uri.Builder().appendQueryParameter("key1", "value1").build()
        val observable = Observable.just(R.drawable.kakaostory_animated)
                 .map { resId ->
                    File(FileHelper.writeStoryImage(RuntimeEnvironment.application, resId)) }
                 .map { file ->
                    val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
                    return@map listOf(MultipartBody.Part.createFormData("file", file.name, requestBody)) }
                 .flatMap { images -> api.scrapImages(images) }
                .map { urls -> Gson().toJson(urls) }
                 .flatMap { urls -> api.postPhoto(urls, "content", Story.Permission.PUBLIC, true, uri, uri, uri, uri) } as Observable<StoryPostResponse>

        observable.subscribe { response -> ShadowLog.e("imageUrls", response.toString())}
    }
}
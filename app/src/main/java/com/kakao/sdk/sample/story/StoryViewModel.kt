package com.kakao.sdk.sample.story

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.gson.Gson
import com.kakao.sdk.kakaostory.data.KakaoStoryApiClient
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.login.data.AuthApiClient
import com.kakao.sdk.login.data.MissingScopesError
import retrofit2.HttpException

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
class StoryViewModel(private val storyApiClient: KakaoStoryApiClient, private val authApiClient: AuthApiClient) : ViewModel() {
    val isStoryUser = MutableLiveData<Boolean>()
    val stories = MutableLiveData<List<Story>>()
    val requiredScopes = MutableLiveData<List<String>>()

    fun isStoryUser() {
        val disposable = storyApiClient.isStoryUser().toObservable()
                .retryWhen { authApiClient.refreshTokenObservable(it) }
                .subscribe(
                        { response -> isStoryUser.postValue(response.isStoryUser)},
                        { error -> Log.e("isStoryUser", error.toString())})
    }

    fun getMyStories() {
        val disposable = storyApiClient.getMyStories().toObservable()
                .retryWhen { authApiClient.refreshTokenObservable(it) }
                .subscribe(
                        { response -> stories.postValue(response)},
                        { t ->
                            if (t is HttpException && t.code() == 403) {
                                val errorString = t.response().errorBody()?.string()
                                val error = Gson().fromJson(errorString, MissingScopesError::class.java)
                                if (error.code == -402) {
                                    requiredScopes.postValue(error.requiredScopes)
                                }
                            }
                        }
                )
    }
}
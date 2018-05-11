package com.kakao.sdk.kakaostory.domain

import com.kakao.sdk.kakaostory.data.DefaultStoryApiJavaClient

/**
 * @author kevin.kang. Created on 2018. 5. 9..
 */
interface StoryApiJavaClient {
    companion object {
        val instance by lazy {
            DefaultStoryApiJavaClient() as StoryApiJavaClient
        }
    }
}
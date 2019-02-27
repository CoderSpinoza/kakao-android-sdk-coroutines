package com.kakao.sdk.kakaostory

import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.common.Utility
import com.kakao.sdk.network.ApiFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

/**
 * @author kevin.kang. Created on 2018. 3. 20..
 */
class StoryApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: StoryApi

    @BeforeEach fun setup() {
        server = MockWebServer()
        api = ApiFactory.withClient(server.url("/").toString(), OkHttpClient.Builder())
                .create(StoryApi::class.java)
    }

    @AfterEach fun cleanup() {
        server.shutdown()
    }

    @Test fun isStoryUser() {
        server.enqueue(
                MockResponse().setResponseCode(200)
                        .setBody(Utility.getJson("json/story_user.json")))
        runBlocking {
            api.isStoryUser()
        }
        val request = server.takeRequest()

        assertEquals("GET", request.method)
        assertEquals(Constants.IS_STORY_USER_PATH, request.requestUrl.encodedPath())
    }

    @MethodSource("booleanProvider")
    @ParameterizedTest fun profile(secureResource: Boolean?) {
        server.enqueue(MockResponse().setResponseCode(200)
                .setBody(Utility.getJson("json/profile/profile.json")))
        runBlocking {
            api.profile(secureResource)
        }
        val request = server.takeRequest()
        val params = Utility.parseQuery(request.requestUrl.query())

        assertEquals("GET", request.method)
        assertEquals(Constants.STORY_PROFILE_PATH, request.requestUrl.encodedPath())
        if (secureResource == null) {
            assertFalse(params.containsKey(Constants.SECURE_RESOURCE))
            return
        }
        assertEquals(secureResource.toString(), params[Constants.SECURE_RESOURCE])
    }

    @ValueSource(strings = ["", "last_id"])
    @ParameterizedTest fun myStory(id: String) {
        server.enqueue(MockResponse().setResponseCode(200)
                .setBody(Utility.getJson("json/story.json")))
        runBlocking {
            api.myStory(id)
        }
        val request = server.takeRequest()

        val params = Utility.parseQuery(request.requestUrl.query())

        assertEquals("GET", request.method)
        assertEquals(Constants.GET_STORY_PATH, request.requestUrl.encodedPath())
        assertEquals(id, params[Constants.ID])
    }

    @MethodSource("stringProvider")
    @ParameterizedTest fun myStories(id: String?) {
        server.enqueue(MockResponse().setResponseCode(200)
                .setBody(Utility.getJson("json/stories.json")))
        runBlocking {
            api.myStories(id)
        }
        val request = server.takeRequest()
        val params = Utility.parseQuery(request.requestUrl.query())

        assertEquals("GET", request.method)
        assertEquals(Constants.GET_STORIES_PATH, request.requestUrl.encodedPath())
        assertEquals(id, params[Constants.LAST_ID])
    }

    @MethodSource("postNoteProvider")
    @ParameterizedTest fun postNote(
        content: String,
        permission: Story.Permission,
        enableShare: Boolean,
        params1: String?,
        params2: String?,
        params3: String?,
        params4: String?
    ) {
        server.enqueue(MockResponse().setResponseCode(200)
                .setBody(Utility.getJson("json/post.json")))
        runBlocking {
            api.postNote(content, permission, enableShare, params1, params2, params3, params4)
        }
        val request = server.takeRequest()
        val params = Utility.parseQuery(request.body.readUtf8())

        assertEquals("POST", request.method)
        assertEquals(Constants.POST_NOTE_PATH, request.requestUrl.encodedPath())
        assertEquals(content, params[Constants.CONTENT])
        assertEquals(permission.value, params[Constants.PERMISSION])
        assertEquals(enableShare.toString(), params[Constants.ENABLE_SHARE])

        assertEquals(params1, params[Constants.ANDROID_EXEC_PARAM])
        assertEquals(params2, params[Constants.IOS_EXEC_PARAM])
        assertEquals(params3, params[Constants.ANDROID_MARKET_PARAM])
        assertEquals(params4, params[Constants.IOS_MARKET_PARAM])
    }

//    @MethodSource("postPhotoProvider")
//    @ParameterizedTest fun postPhoto(images: String,
//                                     content: String,
//                                     permission: Story.Permission,
//                                     enableShare: Boolean,
//                                     androidExecParams: String?,
//                                     iosExecParams: String?,
//                                     androidMarketParams: String?,
//                                     iosMarketParams: String?) {
//        api.postPhoto(images, content, permission, enableShare, androidExecParams, iosExecParams, androidMarketParams, iosMarketParams)
//                .subscribe(TestObserver<StoryPostResponse>())
//        val request = server.takeRequest()
//        val params = Utility.parseQuery(request.body.readUtf8())
//
//        assertEquals("POST", request.method)
//        assertEquals(Constants.POST_PHOTO_PATH, request.requestUrl.encodedPath())
//        assertEquals(images, params[Constants.IMAGE_URL_LIST])
//        assertEquals(content, params[Constants.CONTENT])
//        assertEquals(permission.value, params[Constants.PERMISSION])
//        assertEquals(enableShare.toString(), params[Constants.ENABLE_SHARE])
//        assertEquals(androidExecParams, params[Constants.ANDROID_EXEC_PARAM])
//        assertEquals(iosExecParams, params[Constants.IOS_EXEC_PARAM])
//    }

    @Disabled
    @ParameterizedTest fun postLink() {
    }

    @ValueSource(strings = ["", "last_id"])
    @ParameterizedTest fun deleteStory(id: String) {
        server.enqueue(MockResponse().setResponseCode(200))
        runBlocking {
            api.deleteStory(id)
        }
        val request = server.takeRequest()
        val params = Utility.parseQuery(request.requestUrl.query())

        assertEquals("DELETE", request.method)
        assertEquals(Constants.DELETE_STORY_PATH, request.requestUrl.encodedPath())
        assertEquals(id, params[Constants.ID])
    }

    @ValueSource(strings = ["", "scrap_url"])
    @ParameterizedTest fun scrapLink(url: String) {
        server.enqueue(MockResponse().setResponseCode(200)
                .setBody(Utility.getJson("json/linkinfo/linkinfo1.json")))
        runBlocking {
            api.scrapLink(url)
        }
        val request = server.takeRequest()
        val params = Utility.parseQuery(request.requestUrl.query())

        assertEquals("GET", request.method)
        assertEquals(Constants.SCRAP_LINK_PATH, request.requestUrl.encodedPath())
        assertEquals(url, params[Constants.URL])
    }

    // TODO
    @Disabled
    @ParameterizedTest fun scrapImages() {
    }

    companion object {
        @Suppress("unused")
        @JvmStatic fun booleanProvider(): Stream<Arguments> {
            return Stream.of(true, false, null).map { Arguments.of(it) }
        }

        @Suppress("unused")
        @JvmStatic fun stringProvider(): Stream<Arguments> {
            return Stream.of(null, "", "last_id").map { Arguments.of(it) }
        }

        @Suppress("unused")
        @JvmStatic fun postNoteProvider(): Stream<Arguments> {
            return Stream.of(
                    Arguments.of(
                            "content",
                            Story.Permission.PUBLIC,
                            true,
                            null, null, null, null
                    ),
                    Arguments.of(
                            "content",
                            Story.Permission.FRIEND,
                            false,
                            "key1=value1", "key1=value1", "key1=value1", "key1=value1"
                    ),
                    Arguments.of(
                            "escaped \"content\"",
                            Story.Permission.ONLY_ME,
                            true,
                            "key1=value1", null, "key2=value2", null)
            )
//            return Stream.of(Triple("content", Story.Permission.PUBLIC, true))
//                    .map { Arguments.of(it.first, it.second, it.third) }
        }
//
//        @Suppress("unused")
//        @JvmStatic fun postLinkProvider(): Stream<Arguments> {
//
//        }
//
//        @JvmStatic fun postPhotoProvider(): Stream<Arguments> {
//
//        }
    }
}
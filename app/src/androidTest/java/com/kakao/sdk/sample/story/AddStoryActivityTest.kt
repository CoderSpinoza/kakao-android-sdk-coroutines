package com.kakao.sdk.sample.story

import android.app.Instrumentation
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import android.os.SystemClock
import org.junit.Rule
import org.junit.Test

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.runner.AndroidJUnit4
import android.util.Log
import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.kakao.sdk.sample.R
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit

import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoRule
import javax.inject.Inject

/**
 * @author kevin.kang. Created on 2018. 5. 24..
 */
@RunWith(AndroidJUnit4::class)
class AddStoryActivityTest {
    private val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()

    @Rule @JvmField
    val rule = ActivityTestRule(AddStoryActivity::class.java, false, false)
    @Rule @JvmField val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var viewModel: AddStoryViewModel
    private val mockText = MutableLiveData<String>()
    private val mockCanPost = MutableLiveData<Boolean>()

    @Before fun setupEach() {
        val application = instrumentation.targetContext.applicationContext as TestApplication
        MockitoAnnotations.initMocks(this)
        application.component().inject(this)
        viewModel = viewModelFactory.create(AddStoryViewModel::class.java)
        Log.e("Test", "after create")
//        Log.e("Test", "create viewModel: ${viewModel2.toString()}")
        doNothing().`when`(viewModel).setContent(anyString())
        doReturn(mockCanPost).`when`(viewModel).canPost()
//        viewModelFactory = ViewModelUtil.createFor(viewModel)
//        rule.activity.viewModelFactory = viewModelFactory
        Log.e("Test", "finished mocking")
        Log.e("Test", "mocked viewModel: $viewModel")
        rule.launchActivity(null)
    }

    @Test fun testVisible() {
        onView(withId(R.id.content_edit_text))
                .check(matches(isDisplayed()))
                .check(matches(isDisplayingAtLeast(100)))
        onView(withId(R.id.image_upload_button))
                .check(matches(isDisplayed()))
                .check(matches(isDisplayingAtLeast(100)))
        onView(withId(R.id.upload_image_view))
                .check(matches(isDisplayed()))
                .check(matches(isDisplayingAtLeast(100)))
        onView(withId(R.id.post))
                .check(matches(isDisplayed()))
                .check(matches(isDisplayingAtLeast(100)))
    }

    @Test fun postWithSomeContent() {
        onView(withId(R.id.post))
                .check(matches(not(isEnabled())))
        onView(withId(R.id.content_edit_text))
                .perform(clearText(), typeText("This is sample content"))

        SystemClock.sleep(3000)
        rule.runOnUiThread { mockCanPost.value = true }
        onView(withId(R.id.post))
                .check(matches(isEnabled()))
                .perform(click())
    }
}
package com.kakao.sdk.sample

import com.kakao.sdk.sample.friends.FriendsFragment
import com.kakao.sdk.sample.link.LinkFragment
import com.kakao.sdk.sample.story.AddStoryActivity
import com.kakao.sdk.sample.story.StoryDetailFragment
import com.kakao.sdk.sample.story.StoryFragment
import com.kakao.sdk.sample.talk.TalkFragment
import com.kakao.sdk.sample.user.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author kevin.kang. Created on 2018. 5. 17..
 */
@Module
abstract class ApplicationModule {
    @ContributesAndroidInjector
    abstract fun friendsFragmentInjector(): FriendsFragment

    @ContributesAndroidInjector
    abstract fun talkFragmentInjector(): TalkFragment

    @ContributesAndroidInjector
    abstract fun storyFragmentInjector(): StoryFragment

    @ContributesAndroidInjector
    abstract fun storyDetailFragmentInjector(): StoryDetailFragment

    @ContributesAndroidInjector
    abstract fun linkFragmentInjector(): LinkFragment

    @ContributesAndroidInjector
    abstract fun userFragmentInjector(): UserFragment

    @ContributesAndroidInjector
    abstract fun addStoryActivityInjector(): AddStoryActivity
}
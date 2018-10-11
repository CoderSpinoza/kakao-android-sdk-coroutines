package com.kakao.sdk.sample

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kakao.sdk.sample.friends.FriendsViewModel
import com.kakao.sdk.sample.story.AddStoryViewModel
import com.kakao.sdk.sample.story.StoryViewModel
import com.kakao.sdk.sample.talk.TalkViewModel
import com.kakao.sdk.sample.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author kevin.kang. Created on 2018. 5. 17..
 */
@Module
abstract class ViewModelModule {
    @Binds internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds @IntoMap @ViewModelKey(FriendsViewModel::class)
    internal abstract fun friendsViewModel(viewModel: FriendsViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(TalkViewModel::class)
    internal abstract fun talkViewModel(viewModel: TalkViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(StoryViewModel::class)
    internal abstract fun storyViewModel(viewModel: StoryViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(AddStoryViewModel::class)
    internal abstract fun addStoryViewModel(viewModel: AddStoryViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(UserViewModel::class)
    internal abstract fun userViewModel(viewModel: UserViewModel): ViewModel
}
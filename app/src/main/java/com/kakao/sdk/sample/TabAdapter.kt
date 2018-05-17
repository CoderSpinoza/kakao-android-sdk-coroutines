package com.kakao.sdk.sample

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.kakao.sdk.sample.friends.FriendsFragment
import com.kakao.sdk.sample.link.LinkFragment
import com.kakao.sdk.sample.story.StoryFragment
import com.kakao.sdk.sample.talk.TalkFragment
import com.kakao.sdk.sample.user.UserFragment

/**
 * @author kevin.kang. Created on 2018. 5. 15..
 */
class TabAdapter(context: Context, fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    val tabTitles = listOf<String>()

    val hostFragments = listOf(
            HostFragment.newInstance(FriendsFragment(), context.getString(R.string.friends)),
            HostFragment.newInstance(TalkFragment(), context.getString(R.string.kakaotalk)),
            HostFragment.newInstance(StoryFragment(), context.getString(R.string.kakaostory)),
            HostFragment.newInstance(LinkFragment(), context.getString(R.string.kakaolink)),
            HostFragment.newInstance(UserFragment(), context.getString(R.string.user))
    )

    override fun getItem(position: Int): Fragment {
        return hostFragments[position]
    }

    override fun getCount(): Int {
        return hostFragments.size
    }
}
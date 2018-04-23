package com.kakao.sdk.sample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kakao.sdk.login.domain.AccessTokenRepo
import com.kakao.sdk.sample.databinding.ActivityMainBinding
import com.kakao.sdk.sample.link.LinkFragment
import com.kakao.sdk.sample.story.StoryFragment
import com.kakao.sdk.sample.talk.TalkFragment
import com.kakao.sdk.sample.user.UserFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val menuIds = arrayOf(R.id.menu_talk, R.id.menu_story, R.id.menu_link, R.id.menu_user)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("Token on MainActivity", AccessTokenRepo.instance.fromCache().toString())
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        bottom_nav.setOnNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.menu_talk -> {
                    binding.viewPager.currentItem = 0
                    title = menu.title
                }
                R.id.menu_story -> {
                    binding.viewPager.currentItem = 1
                    title = menu.title
                }
                R.id.menu_link -> {
                    binding.viewPager.currentItem = 2
                    title = menu.title
                }
                R.id.menu_user -> {
                    binding.viewPager.currentItem = 3
                    title = menu.title
                }
            }
            true
        }

        view_pager.offscreenPageLimit = 4
        setupViewPager(view_pager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val talk = TalkFragment()
        val story = StoryFragment()
        val link = LinkFragment()
        val user = UserFragment()

        val fragments = listOf(talk, story, link, user)
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bottom_nav.selectedItemId = menuIds[position]
            }

        })
        bottom_nav.selectedItemId = menuIds[0]
    }
}

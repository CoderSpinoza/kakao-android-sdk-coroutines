package com.kakao.sdk.sample

import androidx.databinding.DataBindingUtil
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kakao.sdk.sample.databinding.ActivityMainBinding

import com.kakao.sdk.sample.story.StoryDetailFragment
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), StoryDetailFragment.OnFragmentInteractionListener {

    val drawables = listOf(R.drawable.icon_user, R.drawable.icon_talk, R.drawable.icon_story, R.drawable.icon_push, R.drawable.icon_user)

    lateinit var tabAdapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        view_pager.offscreenPageLimit = 4
        setupViewPager(view_pager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        tabAdapter = TabAdapter(this, supportFragmentManager)
        viewPager.adapter = tabAdapter
        val tabViews = Observable.fromIterable(drawables)
                .map { Pair(it, LayoutInflater.from(this@MainActivity).inflate(R.layout.item_tab, null)) }
                .doOnNext { it.second.findViewById<ImageView>(R.id.tab_icon).setImageDrawable(resources.getDrawable(it.first)) }
                .map { it.second }

        val tabs = Observable.fromIterable(tabAdapter.hostFragments)
                .map { bottom_tab.newTab().setContentDescription(it.title) }
                .doOnNext { bottom_tab.addTab(it) }



        Observable.zip(tabViews, tabs, BiFunction<View, TabLayout.Tab, TabLayout.Tab> { view, tab ->
            tab.customView = view
            return@BiFunction tab
        }).subscribe()

        bottom_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

        })

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bottom_tab.getTabAt(position)?.select()
            }
        })

        bottom_tab.getTabAt(0)?.select()
    }

    override fun showUpButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun hideUpButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun goToStoryDetail(storyId: String) {
        val host = tabAdapter.getItem(view_pager.currentItem) as HostFragment
        host.replaceFragment(StoryDetailFragment.newInstance(storyId), true)
    }

    override fun onBackPressed() {
        if (!HostFragment.handleBackPressed(supportFragmentManager)) {
            super.onBackPressed()
        }
    }
}

package com.kakao.sdk.sample.story

import android.support.v7.widget.RecyclerView
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.sample.databinding.ItemStoryBinding

/**
 * @author kevin.kang. Created on 2018. 4. 23..
 */
class StoryHolder(val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(story: Story) {
        binding.story = story
        binding.executePendingBindings()
    }
}
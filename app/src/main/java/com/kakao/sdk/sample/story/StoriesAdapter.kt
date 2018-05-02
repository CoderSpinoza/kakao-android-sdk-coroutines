package com.kakao.sdk.sample.story

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.sample.databinding.ItemStoryBinding

/**
 * @author kevin.kang. Created on 2018. 4. 23..
 */
class StoriesAdapter(var stories: List<Story>): RecyclerView.Adapter<StoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStoryBinding.inflate(inflater, parent, false)
        return StoryHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        holder.bind(stories[position])
    }

    override fun getItemCount(): Int {
        return stories.size
    }
}
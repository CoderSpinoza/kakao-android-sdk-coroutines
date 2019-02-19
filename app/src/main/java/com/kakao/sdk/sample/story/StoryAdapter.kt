package com.kakao.sdk.sample.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kakao.sdk.kakaostory.entity.Story
import com.kakao.sdk.sample.databinding.ItemStoryBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author kevin.kang. Created on 2018. 4. 23..
 */
class StoryAdapter(var stories: List<Story>): RecyclerView.Adapter<StoryHolder>() {
    private val clickSubject = PublishSubject.create<Story>()
    val clickEvents: Observable<Story> = clickSubject.hide()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStoryBinding.inflate(inflater, parent, false)
        return StoryHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        holder.bind(stories[position])
        holder.binding.root.setOnClickListener { clickSubject.onNext(stories[position]) }
    }

    override fun getItemCount(): Int {
        return stories.size
    }
}
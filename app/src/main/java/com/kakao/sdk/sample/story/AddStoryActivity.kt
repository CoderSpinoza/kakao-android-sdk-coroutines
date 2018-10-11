package com.kakao.sdk.sample.story

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.ActivityAddStoryBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class AddStoryActivity : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivityAddStoryBinding
    lateinit var viewModel: AddStoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
//        Log.e("AddStoryActivity", "onCreate")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_story)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddStoryViewModel::class.java)
        Log.e("AddStoryActivity", "activity viewModel: ${viewModel.toString()}")
    }

    override fun onResume() {
        super.onResume()
//        binding.contentEditText.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                viewModel.setContent(s.toString())
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//        })
//        Log.e("AddStoryActivity", "" + viewModel)
//        Log.e("AddStoryActivity", "beforeObserve")
        viewModel.canPost().observe(this, Observer {
            binding.post.isEnabled = it ?: false
        })
    }
}

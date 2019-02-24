package com.kakao.sdk.sample.story

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
        Log.e("AddStoryActivity", "activity viewModel: $viewModel")
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

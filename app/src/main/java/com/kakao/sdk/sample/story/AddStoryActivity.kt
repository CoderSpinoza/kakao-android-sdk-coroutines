package com.kakao.sdk.sample.story

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.ActivityAddStoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    val addStoryViewModel: AddStoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.e("AddStoryActivity", "onCreate")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_story)
        Log.e("AddStoryActivity", "activity viewModel: $addStoryViewModel")
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
        addStoryViewModel.canPost().observe(this, Observer {
            binding.post.isEnabled = it ?: false
        })
    }
}

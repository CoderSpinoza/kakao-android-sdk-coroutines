package com.kakao.sdk.sample.user

import androidx.lifecycle.Observer
import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kakao.sdk.sample.Navigator
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FragmentUserBinding
import com.kakao.sdk.sample.databinding.ViewUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *
 */
class UserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private lateinit var binding: FragmentUserBinding
    private lateinit var navigator: Navigator
    val userViewModel: UserViewModel by viewModel()
    val tokenViewModel: TokenViewModel by viewModel()

    val logoutObserver = Observer<Void> {
        navigator.redirectToLogin(context!!)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        val view = binding.root
        DataBindingUtil.inflate(inflater, R.layout.view_user, container, false)
                as ViewUserBinding

        binding.lifecycleOwner = this

        navigator = Navigator.instance
        binding.userViewModel = userViewModel
        binding.tokenViewModel = tokenViewModel
        binding.userViewModel?.logoutEvent?.observe(this, logoutObserver)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (this.isVisible and isVisibleToUser) {
            binding.userViewModel?.loadProfile()
            binding.tokenViewModel?.isVisible()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_user, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_logout -> binding.userViewModel?.logout()
            R.id.menu_unlink -> binding.userViewModel?.unlink()
        }
        return super.onOptionsItemSelected(item)
    }
}

@BindingAdapter("bind:imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        Glide.with(imageView.context).load(imageUrl).into(imageView)
        return
    }
    Glide.with(imageView.context).load(R.drawable.thumb_talk).into(imageView)
}
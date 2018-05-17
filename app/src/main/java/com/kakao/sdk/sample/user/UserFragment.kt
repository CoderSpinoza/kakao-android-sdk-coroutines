package com.kakao.sdk.sample.user

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kakao.sdk.sample.Navigator
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.ViewModelFactory
import com.kakao.sdk.sample.databinding.FragmentUserBinding
import com.kakao.sdk.sample.databinding.ViewUserBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

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

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        val view = binding.root
        val userBinding = DataBindingUtil.inflate(inflater, R.layout.view_user, container, false) as ViewUserBinding

        binding.setLifecycleOwner(this)

        navigator = Navigator.instance

        binding.userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        binding.tokenViewModel = ViewModelProviders.of(this, viewModelFactory).get(TokenViewModel::class.java)

        binding.userViewModel?.logoutEvent?.observe(this, Observer {
            navigator.redirectToLogin(context!!)
        })
        return view
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
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
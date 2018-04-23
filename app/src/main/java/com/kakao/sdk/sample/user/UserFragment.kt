package com.kakao.sdk.sample.user

import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kakao.sdk.login.data.UserApiClient
import com.kakao.sdk.login.domain.AccessTokenRepo
import com.kakao.sdk.sample.BaseFragment
import com.kakao.sdk.sample.LogoutNavigator
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FragmentUserBinding
import com.kakao.sdk.sample.databinding.ViewUserBinding

/**
 *
 */
class UserFragment : BaseFragment(), LogoutNavigator {
    override fun onLogout() {
        redirectToLogin()
    }

    private lateinit var binding: FragmentUserBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        val view = binding.root
        val userBinding = DataBindingUtil.inflate(inflater, R.layout.view_user, container, false) as ViewUserBinding

        binding.setLifecycleOwner(this)

        binding.userViewModel = UserViewModel(UserApiClient.instance)
        binding.tokenViewModel = TokenViewModel(AccessTokenRepo.instance)

        binding.userViewModel?.logoutEvent?.observe(this, Observer {
            this@UserFragment.onLogout()
        })

        return view
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
        inflater?.inflate(R.menu.menu_talk, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_logout -> binding.userViewModel?.logout()
            R.id.menu_unlink -> binding.userViewModel?.unlink()
        }
        return true
    }

}

@BindingAdapter("bind:imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Log.e("loadImage", "called")
    if (imageUrl != null) {
        Glide.with(imageView.context).load(imageUrl).into(imageView)
    }
}
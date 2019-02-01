package com.kakao.sdk.sample.user

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kakao.sdk.sample.Navigator
import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FragmentUserBinding
import com.kakao.sdk.sample.databinding.ViewUserBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.math.log

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

    val logoutObserver = Observer<Void> {
        navigator.redirectToLogin(context!!)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        val view = binding.root
        DataBindingUtil.inflate(inflater, R.layout.view_user, container, false) as ViewUserBinding

        binding.setLifecycleOwner(this)

        navigator = Navigator.instance

        binding.userViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(UserViewModel::class.java)
        binding.tokenViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(TokenViewModel::class.java)

        binding.userViewModel?.logoutEvent?.observe(this, logoutObserver)
        return view
    }

    override fun onDestroyView() {
        if (binding.userViewModel == null) {
            Log.e("onDestroyView", "userViewModel is null.")
        } else {
            Log.e("onDestroyView", "userViewModel is not null")
        }
        Log.e("viewModelStore", viewModelStore.toString())
        super.onDestroyView()
    }

    override fun onDestroy() {
        if (binding.userViewModel == null) {
            Log.e("onDestroy", "userViewModel is null.")
        }
        super.onDestroy()
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
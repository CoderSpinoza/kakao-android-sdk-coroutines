package com.kakao.sdk.sample

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * @author kevin.kang. Created on 2018. 4. 20..
 */
open class HostFragment: Fragment() {
    private lateinit var fragment: Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_host, container, false)
        replaceFragment(fragment, false)
        return view
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        fragment.setMenuVisibility(menuVisible)

        if (isAdded) {
            childFragmentManager.fragments.map { it.setMenuVisibility(menuVisible) }
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = childFragmentManager.beginTransaction().replace(R.id.hosted_fragment, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun onBackPressed(): Boolean {
        val fm = childFragmentManager
        if (handleBackPressed(fm)) {
            return true
        } else if (userVisibleHint && fm.backStackEntryCount > 0) {
            fm.popBackStackImmediate()
            return true
        }
        return false
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        fragment.userVisibleHint = isVisibleToUser
        if (activity != null) {
            if (title != null) {
                (activity as MainActivity).updateTitle(title!!)
            }
            if (childFragmentManager.backStackEntryCount == 0) {
                (activity as MainActivity).hideUpButton()
            } else {
                (activity as MainActivity).showUpButton()
            }
        }
        super.setUserVisibleHint(isVisibleToUser)
    }

    var title: String? = null
        set(value) {
            if (value != null && activity != null) {
                (activity as MainActivity).updateTitle(value)
            }
            field = value
        }

    companion object {
        @JvmStatic fun newInstance(fragment: Fragment, title: String): HostFragment {
            val hostFragment = HostFragment()
            hostFragment.fragment = fragment
            hostFragment.title = title
            return hostFragment
        }

        fun handleBackPressed(fm: FragmentManager): Boolean {
            for (frag in fm.fragments) {
                if (frag.userVisibleHint && frag is HostFragment) {
                    return frag.onBackPressed()
                }
            }
            return false
        }
    }
}
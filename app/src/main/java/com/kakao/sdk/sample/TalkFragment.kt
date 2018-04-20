package com.kakao.sdk.sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *
 */
class TalkFragment : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TalkFragment", "onCreate called")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        //        FriendsApiClient.instance.getFriends().toObservable()
//                .retryWhen { attempts -> AuthApiClient.instance.refreshTokenObservable(attempts) }
//                .retryWhen { AuthCodeService.instance.updateScopesObservable(this@MainActivity, it) }
//                .subscribeOn(Schedulers.io())
//                .subscribe(
//                        { response -> Log.e("friends response", "" + response.friends.size) },
//                        { Log.e("error", "" + it.message + " " + it.toString()) }
//                )
        return inflater.inflate(R.layout.fragment_talk, container, false)
    }
}

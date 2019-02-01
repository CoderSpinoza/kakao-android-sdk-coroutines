package com.kakao.sdk.sample.talk

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FragmentChatDetailBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChatDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChatDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ChatDetailFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentChatDetailBinding
    private lateinit var viewModel: TalkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_detail, container, false)
        binding.setLifecycleOwner(this)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(TalkViewModel::class.java)
        binding.talkViewModel = viewModel
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ChatDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                ChatDetailFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}

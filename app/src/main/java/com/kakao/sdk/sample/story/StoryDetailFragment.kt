package com.kakao.sdk.sample.story

import androidx.lifecycle.Observer
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kakao.sdk.kakaostory.data.Story
import com.kakao.sdk.sample.HostFragment
import com.kakao.sdk.sample.MainActivity

import com.kakao.sdk.sample.R
import com.kakao.sdk.sample.databinding.FragmentStoryDetailBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val STORY_ID = "storyId"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StoryDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StoryDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StoryDetailFragment : Fragment(), LifecycleOwner {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var storyId: String
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentStoryDetailBinding
    private lateinit var viewModel: StoryViewModel


    private val storyObserver = Observer<Story> {
        updateStoryView(it!!)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            storyId = it.getString(STORY_ID)!!
        }
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story_detail, container, false)
        binding.setLifecycleOwner(this)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(StoryViewModel::class.java)
        binding.storyViewModel = viewModel

        viewModel.selectedStory.observe(this, storyObserver)
        viewModel.getStory(storyId)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_user, menu)
    }

    private fun updateStoryView(story: Story) {
        (parentFragment as HostFragment).title = story.content.substring(0, 10)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as MainActivity
        activity.showUpButton()
//        viewModel.getStory(storyId)
    }

    override fun onPause() {
        super.onPause()
        val activity = activity as MainActivity
        activity.hideUpButton()

        viewModel.selectedStory.removeObserver(storyObserver)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                val activity = activity as MainActivity
                activity.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
        fun showUpButton()
        fun hideUpButton()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param storyId Parameter 1.
         * @return A new instance of fragment StoryDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(storyId: String) =
                StoryDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(STORY_ID, storyId)
                    }
                }
    }
}

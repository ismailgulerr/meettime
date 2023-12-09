package com.ismailguler.meettime.meetings

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ismailguler.meettime.R
import com.ismailguler.meettime.SharedPreferencesUtil
import com.ismailguler.meettime.databinding.FragmentMeetingsBinding
import com.ismailguler.meettime.meetings.MeetingsFragmentDirections

class MeetingsFragment : Fragment(), MeetingsAdapter.MeetingsImpl {

    private var _binding: FragmentMeetingsBinding? = null
    private lateinit var adapter: MeetingsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMeetingsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName = SharedPreferencesUtil(requireContext()).getString(SharedPreferencesUtil.LAST_SELECTED_USER)
        binding.tvWelcome.text = "Hoşgeldin, $userName"
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclerView() {
        val meetingList = mutableListOf<Meeting>(Meeting("Meeting 1", "Code 1", "İsmail Güler"),
            Meeting("Meeting 1", "Code 1", "İsmail Güler"),
            Meeting("Meeting 1", "Code 1", "İsmail Güler"),
            Meeting("Meeting 1", "Code 1", "İsmail Güler"),
            Meeting("Meeting 1", "Code 1", "İsmail Güler"),
            Meeting("Meeting 1", "Code 1", "İsmail Güler"),
            Meeting("Meeting 1", "Code 1", "İsmail Güler"),
            Meeting("Meeting 1", "Code 1", "İsmail Güler"),
            )

        adapter = MeetingsAdapter(meetingList, this)
        binding.rvContent.adapter = adapter
    }

    override fun onClickedMeeting(meeting: Meeting) {
        val bundle = Bundle()
        bundle.putParcelable("meeting", meeting)
        findNavController().navigate(R.id.action_MeetingsFragment_to_MeetingDetailFragment,bundle)
    }
}
package com.ismailguler.meettime.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ismailguler.meettime.R
import com.ismailguler.meettime.SharedPreferencesUtil
import com.ismailguler.meettime.databinding.FragmentMeetingsBinding

class HomeFragment : Fragment(), MeetingsAdapter.MeetingsImpl {

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
        binding.btnCreateMeeting.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionMeetingsFragmentToCreateMeetingFragment())
        }
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclerView() {
        val meetingList: MutableList<Meeting> = SharedPreferencesUtil(requireContext()).getCreatedMeetingList()
        adapter = MeetingsAdapter(requireContext(), meetingList, this)
        binding.rvContent.adapter = adapter
    }

    override fun onClickedMeeting(meeting: Meeting) {
        val bundle = Bundle()
        bundle.putParcelable("meeting", meeting)
        findNavController().navigate(R.id.action_MeetingsFragment_to_MeetingDetailFragment,bundle)
    }

    override fun onClickedShare(meeting: Meeting) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, meeting.code)
        val shareAction = Intent.createChooser(shareIntent, "Kodu Paylaş")
        startActivity(shareAction)
    }
}
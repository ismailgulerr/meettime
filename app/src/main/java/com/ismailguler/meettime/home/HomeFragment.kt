package com.ismailguler.meettime.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
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

        binding.btnFindMeeting.setOnClickListener {
            showFindMeetingDialog()
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

    private fun showFindMeetingDialog() {
        val alertDialog = Dialog(requireContext(), R.style.TransparentDialog)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setContentView(R.layout.dialog_find_meeting)
        alertDialog.window!!.setBackgroundDrawableResource(R.color.half_transparent)
        alertDialog.findViewById<AppCompatButton>(R.id.btn_close).setOnClickListener {
            alertDialog.dismiss()
        }
        val etMeetingCode =  alertDialog.findViewById<EditText>(R.id.et_meeting_code)

        alertDialog.findViewById<AppCompatButton>(R.id.btn_find_meeting).setOnClickListener {
            val code = etMeetingCode.text.toString()
            if (code.length == 6) {
                alertDialog.dismiss()
                findAndNavigateToMeeting(code)
            } else {
                Toast.makeText(requireContext(),"Toplantı kodu 6 haneli olmalıdır.", Toast.LENGTH_LONG).show()
            }
        }
        alertDialog.show()
    }

    private fun findAndNavigateToMeeting(code: String) {
        val sh = SharedPreferencesUtil(requireContext())
        sh.getAllMeetings().find { it.code == code }?.let { meeting ->
            onClickedMeeting(meeting)
        }
    }
}
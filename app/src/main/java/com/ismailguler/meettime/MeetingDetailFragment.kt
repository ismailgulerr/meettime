package com.ismailguler.meettime

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ismailguler.meettime.databinding.FragmentMeetingDetailBinding
import com.ismailguler.meettime.home.Meeting

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MeetingDetailFragment : Fragment() {

    private var _binding: FragmentMeetingDetailBinding? = null
    var meeting: Meeting? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMeetingDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        bundle?.let {
            meeting = it.getParcelable("meeting")
        }
        (activity as? MainActivity)?.setPageTitle(meeting?.title ?: "Toplantı Detay")

        meeting?.let {
            initViews(it)
        }
    }

    private fun initViews(meeting: Meeting) {
        binding.apply {
            tvMeetingTitle.text = meeting.title
            tvMeetingDesc.text = meeting.description
            tvMeetingDate.text = meeting.date
            tvMeetingTime.text = meeting.time
            tvMeetingOwner.text = meeting.owner
            tvMeetingCode.text = meeting.code

            btnCopy.setOnClickListener {
                meeting.let {
                    onClickedShare(it)
                }
            }

            val sh = SharedPreferencesUtil(requireContext())
            btnApply.isVisible = meeting.owner != sh.getCurrentUser()

            if (meeting.getParticipants().contains(sh.getCurrentUser())) {
                btnApply.text = "Toplantıya Kaydoldunuz"
            } else {
                btnApply.text = "Toplantıya Kaydolun"
                btnApply.setOnClickListener {
                    meeting.addParticipant(sh.getCurrentUser())
                    sh.updateMeeting(meeting)
                    btnApply.setOnClickListener {  }
                    initViews(meeting)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClickedShare(meeting: Meeting) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, meeting.code)
        val shareAction = Intent.createChooser(shareIntent, "Kodu Paylaş")
        startActivity(shareAction)
    }
}
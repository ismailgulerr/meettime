package com.ismailguler.meettime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ismailguler.meettime.databinding.FragmentSecondBinding
import com.ismailguler.meettime.home.Meeting

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MeetingDetailFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    var meeting: Meeting? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        bundle?.let {
            meeting = it.getParcelable("meeting")
        }
        (activity as? MainActivity)?.setPageTitle(meeting?.title ?: "Toplantı Detay")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.ismailguler.meettime.home

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ismailguler.meettime.R
import com.ismailguler.meettime.databinding.ItemMeetingBinding
import java.util.UUID

class MeetingsAdapter(val context: Context, private val meetingList: List<Meeting>, val listener: MeetingsImpl) :
    RecyclerView.Adapter<MeetingsAdapter.MeetingViewHolder>() {

    class MeetingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMeetingBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_meeting, parent, false)
        return MeetingViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val currentMeeting = meetingList[position]
        holder.binding.tvMeetingTitle.text = currentMeeting.title
        holder.binding.tvMeetingCode.text = currentMeeting.code
        holder.binding.root.setOnClickListener { listener.onClickedMeeting(currentMeeting) }
        holder.binding.btnCopy.setOnClickListener {
            listener.onClickedShare(currentMeeting)
        }
    }

    override fun getItemCount(): Int {
        return meetingList.size
    }

    interface MeetingsImpl {
        fun onClickedMeeting(meeting: Meeting)
        fun onClickedShare(meeting: Meeting)
    }
}
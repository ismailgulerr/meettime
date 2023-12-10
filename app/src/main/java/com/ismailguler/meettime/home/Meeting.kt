package com.ismailguler.meettime.home

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Meeting(var title: String,
              var code: String,
              var owner: String,
              var description: String,
              var date: String,
              var time: String,
              private var participants: MutableList<String> = mutableListOf()
): Parcelable {
    fun addParticipant(name: String) {
        if (participants == null) {
            participants = mutableListOf()
        }

        if (!participants.contains(name)) {
            participants.add(name)
        }
    }

    fun getParticipants(): MutableList<String> {
        if (participants == null) {
            participants = mutableListOf()
        }
        return participants
    }
}
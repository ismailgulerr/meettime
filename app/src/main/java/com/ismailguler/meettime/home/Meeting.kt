package com.ismailguler.meettime.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Meeting(var title: String,
              var code: String,
              var owner: String,
              var description: String,
              var date: String,
              var time: String,
              ): Parcelable
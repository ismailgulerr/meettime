package com.ismailguler.meettime.meetings

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Meeting(var title: String, var code: String, var owner: String): Parcelable
package com.ismailguler.meettime.chooseUser

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ismailguler.meettime.R
import com.ismailguler.meettime.databinding.ItemMeetingBinding
import com.ismailguler.meettime.databinding.ItemUserBinding

class UsersAdapter(private val userList: List<String>, val listener: UsersAdapterImpl) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemUserBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.binding.tvUserName.text = user
        holder.binding.root.setOnClickListener { listener.onClickedUser(user) }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    interface UsersAdapterImpl {
        fun onClickedUser(user: String)
    }
}
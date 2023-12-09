package com.ismailguler.meettime.chooseUser

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ismailguler.meettime.SharedPreferencesUtil
import com.ismailguler.meettime.databinding.FragmentChooseUserBinding


class ChooseUserFragment : Fragment(), UsersAdapter.UsersAdapterImpl {

    private var _binding: FragmentChooseUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclerView() {
        val users = SharedPreferencesUtil(requireContext()).getUsers()
        adapter = UsersAdapter(users, this)
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun onClickedUser(user: String) {

    }

}
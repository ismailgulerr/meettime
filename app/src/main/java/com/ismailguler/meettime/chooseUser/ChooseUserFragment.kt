package com.ismailguler.meettime.chooseUser

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ismailguler.meettime.R
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

        binding.btnAddUser.setOnClickListener {
            showAddUserDialog()
        }
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

    private fun showAddUserDialog() {
        val alertDialog = Dialog(requireContext(), R.style.TransparentDialog)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setContentView(R.layout.dialog_add_user)
        alertDialog.window!!.setBackgroundDrawableResource(R.color.half_transparent)
        alertDialog.findViewById<AppCompatButton>(R.id.btn_close).setOnClickListener {
            alertDialog.dismiss()
        }
        val etUserName =  alertDialog.findViewById<EditText>(R.id.et_username)

        alertDialog.findViewById<AppCompatButton>(R.id.btn_add).setOnClickListener {
            val name = etUserName.text.toString()
            if (name.length > 2) {
                addNewUser(name)
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }

    private fun addNewUser(userName: String) {
        SharedPreferencesUtil(requireContext()).addNewUser(userName)
        setupRecyclerView()
    }

}
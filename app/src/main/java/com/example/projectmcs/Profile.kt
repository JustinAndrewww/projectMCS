package com.example.projectmcs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.projectmcs.databinding.ActivityLoginPageBinding

class Profile : Fragment() {

    lateinit var username: String
    lateinit var email: String
    var phone: Int = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val logoutButton: Button = view.findViewById(R.id.logout)


        logoutButton.setOnClickListener {
            val intent = Intent(activity, Logout::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val tvUsername: TextView = view.findViewById(R.id.tvUsername)
        val tvEmail: TextView = view.findViewById(R.id.tvEmail)
        val tvPhone: TextView = view.findViewById(R.id.tvPhone)


        tvUsername.text =   "Name   : ${MainActivity.user.username}"
        tvEmail.text =      "Email  : ${MainActivity.user.email}"
        tvPhone.text = "Phone  : ${MainActivity.user.phone}"

    }



}
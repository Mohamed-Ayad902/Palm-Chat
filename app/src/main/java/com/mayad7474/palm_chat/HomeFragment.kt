package com.mayad7474.palm_chat

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val goToChatButton = view.findViewById<Button>(R.id.go_to_chat_button)
        goToChatButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragment_container, ChatFragment())
                addToBackStack(null)
            }
        }
    }
}
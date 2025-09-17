package com.mayad7474.palm_chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {
    private val _messages = MutableLiveData<List<Message>>(emptyList())
    val messages: LiveData<List<Message>> = _messages

    fun setMessages(list: List<Message>) { _messages.value = list }

    fun addMessage(msg: Message) {
        val current = _messages.value ?: emptyList()
        _messages.value = current + msg
    }

}
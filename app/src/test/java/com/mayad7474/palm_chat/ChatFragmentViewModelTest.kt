package com.mayad7474.palm_chat

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner::class)
class ChatFragmentViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<Message>>

    private lateinit var viewModel: ChatViewModel

    @Before
    fun setup() {
        viewModel = ChatViewModel()
        viewModel.messages.observeForever(observer)
    }

    @Test
    fun testMessagesObserverReceivesCorrectData() {
        val messages = listOf(Message("1", "Hello"), Message("2", "World"))
        viewModel.setMessages(messages)
        verify(observer).onChanged(messages)
        assertEquals(messages, viewModel.messages.value)
    }

    @Test
    fun addMessage_appendsToExistingList() {
        val vm = ChatViewModel()
        vm.setMessages(listOf(Message("1", "first")))
        vm.addMessage(Message("2", "second"))

        val expected = listOf(Message("1","first"), Message("2","second"))
        assertEquals(expected, vm.messages.value)
    }
}

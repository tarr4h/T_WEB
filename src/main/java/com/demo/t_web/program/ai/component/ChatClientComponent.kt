package com.demo.t_web.program.ai.component

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.stereotype.Component

@Component
class ChatClientComponent(
    builder : ChatClient.Builder
) {
    val chatClient : ChatClient = builder
        .defaultAdvisors(MessageChatMemoryAdvisor(InMemoryChatMemory()))
        .build()
}